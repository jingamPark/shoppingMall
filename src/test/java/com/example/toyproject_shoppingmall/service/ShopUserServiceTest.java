package com.example.toyproject_shoppingmall.service;

import com.example.toyproject_shoppingmall.dto.PassChangeDTO;
import com.example.toyproject_shoppingmall.dto.UserFormDTO;
import com.example.toyproject_shoppingmall.dto.UserPasswordDTO;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.repository.ShopUserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Log4j2
class ShopUserServiceTest {
    @Autowired
    ModelMapper modelMapper = new ModelMapper();
    @Autowired
    ShopUserService shopUserService;
    @Autowired
    ShopUserRepository shopUserRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ShopUser saveUser() {
        ShopUser shopUser = new ShopUser();
        shopUser.setName("길동");
        shopUser.setLoginId("test");
        shopUser.setEmail("a@a.a");
        shopUser.setPassword(passwordEncoder.encode("12341234"));
        shopUser.setTel("010-1111-2222");
        shopUser.setAddress("부천시");
        return shopUserRepository.save(shopUser);
    }

    @Test
    @DisplayName("사용자가 비밀번호 변경시 test")
    public void changePassTest() {

        PassChangeDTO passChangeDTO = new PassChangeDTO();
        passChangeDTO.setCurPassword("12341234");
        passChangeDTO.setNewPassword("78947894");

        ShopUser shopUser = shopUserRepository.findByLoginId(saveUser().getLoginId());

        log.info(shopUser);


        boolean rs =shopUserService.changePassword(passChangeDTO,shopUser.getLoginId());
        log.info(rs + " aaa  : " + shopUser);

        if(passwordEncoder.matches(passChangeDTO.getNewPassword() , shopUser.getPassword())){
            log.info("비밀번호 일치" + passChangeDTO.getNewPassword());
            log.info(shopUser.getName());
        }else {
            log.info("거짓");
            log.info(shopUser.getName());
        }







    }


    @Test
    @DisplayName("비밀번호 분실시 비밀번호 변경")
    public void resetPassword() {
        UserPasswordDTO userPasswordDTO =new UserPasswordDTO();

        userPasswordDTO.setNewPassword("newPass");

        ShopUser newpass= shopUserService.resetPassword(saveUser().getLoginId(), userPasswordDTO);

        log.info(newpass);

    }

    @Test
    @DisplayName("유저 정보변경")
    public void userModify() {
        String loginId = saveUser().getLoginId();
        UserFormDTO userFormDTO = new UserFormDTO();

        userFormDTO.setName("신짱구");
        userFormDTO.setAddress("변경된 주소");
        userFormDTO.setTel("12341234");

        ShopUser shopUser = shopUserService.modify(userFormDTO,loginId);
        log.info(shopUser);

    }




}