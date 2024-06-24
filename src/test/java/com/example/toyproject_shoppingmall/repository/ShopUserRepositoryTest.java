package com.example.toyproject_shoppingmall.repository;

import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.service.ShopUserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Log4j2
class ShopUserRepositoryTest {

    @Autowired
    ShopUserRepository shopUserRepository;


    @Test
    @DisplayName("회원가입 테스트")
    public void createMemberTest() {
        ShopUser shopUser = new ShopUser();

        shopUser.setName("길동");
        shopUser.setLoginId("hong");
        shopUser.setEmail("a@a.2444");
        shopUser.setPassword("12341234");
        shopUser.setTel("010-1111-2222");
        shopUser.setAddress("부천시");



         ShopUser save=  shopUserRepository.save(shopUser);
        log.info(save);

    }


    @Test
    @DisplayName("Auditing 테스트")
    void auditingTest(){
        ShopUser shopUser1= new ShopUser();


        shopUser1.setName("길동");
        shopUser1.setLoginId("hong");
        shopUser1.setEmail("a@a.2444");
        shopUser1.setPassword("12341234");
        shopUser1.setTel("010-1111-2222");
        shopUser1.setAddress("부천시");

        shopUserRepository.save(shopUser1);


       ShopUser shopUser=shopUserRepository.findById(shopUser1.getId())
               .orElseThrow(EntityNotFoundException::new);

        System.out.println("register time: "+ shopUser.getRegTime());
        System.out.println("update time: "+shopUser.getUpdateTime());
        System.out.println("create member: "+shopUser.getModifiedBy());
        System.out.println("update member: "+ shopUser.getCreateBy());
    }

    @Test
    @DisplayName("아이디 찾기")
    void findId(){
        ShopUser shopUser1= new ShopUser();


        shopUser1.setName("길동");
        shopUser1.setLoginId("hong");
        shopUser1.setEmail("a@a.2444");
        shopUser1.setPassword("12341234");
        shopUser1.setTel("010-1111-2222");
        shopUser1.setAddress("부천시");

        shopUserRepository.save(shopUser1);


        ShopUser user = shopUserRepository.findByEmailAndName(shopUser1.getEmail(),shopUser1.getName());
        log.info(user.getLoginId());

    }

    @Test
    @DisplayName("비밀번호 찾기")
    void findPassT(){
        ShopUser shopUser1= new ShopUser();


        shopUser1.setName("길동");
        shopUser1.setLoginId("hong");
        shopUser1.setEmail("a@a.2444");
        shopUser1.setPassword("12341234");
        shopUser1.setTel("010-1111-2222");
        shopUser1.setAddress("부천시");

        shopUserRepository.save(shopUser1);


        ShopUser user = shopUserRepository.findByLoginIdAndEmail(shopUser1.getLoginId(),shopUser1.getEmail());
        log.info(user.getPassword());

    }


}


