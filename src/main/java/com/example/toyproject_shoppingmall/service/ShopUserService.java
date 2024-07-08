package com.example.toyproject_shoppingmall.service;


import com.example.toyproject_shoppingmall.constant.Role;
import com.example.toyproject_shoppingmall.dto.UserPasswordDTO;
import com.example.toyproject_shoppingmall.entity.ShopUser;

import com.example.toyproject_shoppingmall.repository.ShopUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
