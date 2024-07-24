package com.example.toyproject_shoppingmall.service;


import com.example.toyproject_shoppingmall.constant.Role;
import com.example.toyproject_shoppingmall.dto.PassChangeDTO;
import com.example.toyproject_shoppingmall.dto.UserFormDTO;
import com.example.toyproject_shoppingmall.dto.UserPasswordDTO;
import com.example.toyproject_shoppingmall.dto.UserSearchDTO;
import com.example.toyproject_shoppingmall.entity.ShopUser;

import com.example.toyproject_shoppingmall.repository.ShopUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class ShopUserService implements UserDetailsService {


    private final ShopUserRepository shopUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;



    //관리자의 유저정보 변경
    public ShopUser adminUserModify(UserFormDTO userFormDTO,Long shopUserId) {
        //로그인 확인 로그인한 유저아이디와 데이터베이스의id 가져와서 확인

        ShopUser selectUser = shopUserRepository.findById(shopUserId).orElseThrow(EntityNotFoundException::new);
        if (selectUser == null) {
            log.info("없는 유저입니다.");
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다." + shopUserId);
        }

        String pass = passwordEncoder.encode(userFormDTO.getPassword());

        selectUser.setName(userFormDTO.getName());
        selectUser.setId(userFormDTO.getId());
        selectUser.setPassword(pass);
        selectUser.setEmail(userFormDTO.getEmail());
        selectUser.setAddress(userFormDTO.getAddress());
        selectUser.setTel(userFormDTO.getTel());

        return shopUserRepository.save(selectUser);
    }

    // 회원 정보 불러오기
    @Transactional(readOnly = true)
    public UserFormDTO adminUserManagement(long shopUserId) {


        ShopUser findUser = shopUserRepository.findById(shopUserId).orElseThrow(EntityNotFoundException::new);

        if (findUser == null) {
            log.info("다른 유저입니다.");
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다." + findUser.getLoginId());
        }

        UserFormDTO userFormDTO = modelMapper.map(findUser, UserFormDTO.class);

        return userFormDTO;

    }


    //Admin 회원 목록 불러오기
    @Transactional(readOnly = true)
    public Page<ShopUser> getAdminUserListPage(UserSearchDTO userSearchDTO, Pageable pageable) {
        return shopUserRepository.getAdminShopUserPage(userSearchDTO,pageable);
    }


    //비밀번호 변경
    public boolean changePassword(PassChangeDTO passChangeDTO,String loginId) {

        System.out.println("ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ진입");
        
        ShopUser user = shopUserRepository.findByLoginId(loginId);

        boolean result = user.passChange(passChangeDTO,passwordEncoder);

        System.out.println("ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ종료 서비스 결고 result : " + result);

        return result;
    }

    //비밀번호 초기화
    public ShopUser resetPassword(String loginId, UserPasswordDTO userPasswordDTO) {

        ShopUser shopUser = shopUserRepository.findByLoginId(loginId);

        if (shopUser.getLoginId() == null) {
            throw new IllegalStateException("찾을수 없는 회원입니다.");
        }


        shopUser.updatePassword(userPasswordDTO,passwordEncoder);

        return shopUserRepository.save(shopUser);
    }


    //회원가입
    public ShopUser saveUser(ShopUser shopUser) {


        log.info("중복회원 검사 전");
        //중복회원 검사
        validateDuplicateUser(shopUser);
        validateDuplicateEmail(shopUser);
        log.info("중복 회원 검사 후");

        return shopUserRepository.save(shopUser);
    }

    //중복회원 검사
    public void validateDuplicateUser(ShopUser shopUser) {

       ShopUser findUser = shopUserRepository.findByLoginId(shopUser.getLoginId());


        if (findUser != null) {
            log.info("이미 가입된 회원 아이디 입니다.");
            throw new IllegalStateException("이미 가입된 회원 아이디 입니다.");  // 객체 상태가 메서드 호출을 처리하기에 적절치 않을 때

        }

    }

    public void validateDuplicateEmail(ShopUser shopUser) {

        ShopUser findUser = shopUserRepository.findByEmail(shopUser.getEmail());


        if (findUser != null) {
            log.info("이미 가입된 이메일 입니다.");
            throw new IllegalStateException("이미 가입된 회원 이메일 입니다.");  // 객체 상태가 메서드 호출을 처리하기에 적절치 않을 때

        }

    }

    //회원정보
    public UserFormDTO profile(String loginId) {
        //로그인 확인 로그인한 유저아이디와 데이터베이스의id 가져와서 확인

        ShopUser loginUser = shopUserRepository.findByLoginId(loginId);
        if (loginUser == null) {
            log.info("다른 유저입니다.");
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다." + loginId);
        }

        UserFormDTO userFormDTO = modelMapper.map(loginUser, UserFormDTO.class);

        return userFormDTO;
    }

    //회원정보 변경
    public ShopUser modify(UserFormDTO userFormDTO,String loginId) {
        //로그인 확인 로그인한 유저아이디와 데이터베이스의id 가져와서 확인

        ShopUser loginUser = shopUserRepository.findByLoginId(loginId);
        if (loginUser == null) {
            log.info("다른 유저입니다.");
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다." + loginId);
        }


        loginUser.modifyUser(userFormDTO);

        return shopUserRepository.save(loginUser);
    }


    //이름과 이메일로 아이디 찾기

    public ShopUser findLoginId(String email, String name) {


        return shopUserRepository.findByEmailAndName(email,name);
    }

    //아이디와 이름으로 데이터베이스 검색하여 비밀번호 찾기
    public ShopUser findPassword(String loginId, String email) {


        return shopUserRepository.findByLoginIdAndEmail(loginId,email);
    }



    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {

        ShopUser shopUser = shopUserRepository.findByLoginId(loginId);

        if (shopUser == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다." + loginId);
        }
        log.info("현재 로그인 한 유저의 권한"+ equals(shopUser.getRole().name()));
        String role = "";
        if ("ADMIN".equals(shopUser.getRole().name())) {
            log.info("관리자");
            role = Role.ADMIN.name();
        } else {
            log.info("일반유저");
            role = Role.USER.name();
        }

        return User.builder()
                .username(shopUser.getLoginId())
                .password(shopUser.getPassword())
                .roles(role)
                .build();
    }





}
