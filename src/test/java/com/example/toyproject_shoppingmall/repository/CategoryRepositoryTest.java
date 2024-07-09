package com.example.toyproject_shoppingmall.repository;

import com.example.toyproject_shoppingmall.entity.Category;
import com.example.toyproject_shoppingmall.entity.ShopUser;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Log4j2
class CategoryRepositoryTest {
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ShopUserRepository shopUserRepository;


    public ShopUser saveUser() {
        ShopUser shopUser = new ShopUser();
        shopUser.setName("길동");
        shopUser.setLoginId("test");
        shopUser.setEmail("a@a.a");
        shopUser.setPassword("12341234");
        shopUser.setTel("010-1111-2222");
        shopUser.setAddress("부천시");
        return shopUserRepository.save(shopUser);
    }



    @Test
    @DisplayName("카테고리 생성 test")
    public void createCategory() {
        Category category = new Category();
        category.setTitle("테스트");
        category.setDescription("테스트입니다.");
        category.setShopUser(saveUser());

        Category cate= categoryRepository.save(category);

        Optional<Category> cate1 = categoryRepository.findById(cate.getId());
        log.info(cate1);
    }



}