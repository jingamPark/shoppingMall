package com.example.toyproject_shoppingmall.service;

import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.repository.ShopUserRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.swing.text.html.Option;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Log4j2
class ShopUserServiceTest {

    @Autowired
    ShopUserService shopUserService;



    @Test
    @DisplayName("아이디 찾기")
    public void findUserId() {

        ShopUser shopUser = new ShopUser();

        shopUser.setName("길동");
        shopUser.setLoginId("hong");
        shopUser.setEmail("a@a.a");
        shopUser.setPassword("12341234");
        shopUser.setTel("010-1111-2222");
        shopUser.setAddress("부천시");

        ShopUser save=  shopUserService.saveUser(shopUser);


        ShopUser findid = shopUserService.findLoginId(shopUser.getEmail(), shopUser.getName());
        log.info(findid);


    }



}