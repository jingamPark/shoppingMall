package com.example.toyproject_shoppingmall.service;

import com.example.toyproject_shoppingmall.dto.CategoryFormDTO;
import com.example.toyproject_shoppingmall.entity.Category;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.repository.CategoryRepository;
import com.example.toyproject_shoppingmall.repository.ShopUserRepository;
import groovy.util.logging.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@Log4j2

class CategoryServiceTest {
    private static final Logger log = LoggerFactory.getLogger(CategoryServiceTest.class);
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ShopUserRepository shopUserRepository;
    @Autowired
    private CategoryService categoryService;


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

    public ShopUser saveUser2() {
        ShopUser shopUser = new ShopUser();
        shopUser.setName("짱구");
        shopUser.setLoginId("sin");
        shopUser.setEmail("a@a.a");
        shopUser.setPassword("12341234");
        shopUser.setTel("010-1111-2222");
        shopUser.setAddress("부천시");
        return shopUserRepository.save(shopUser);
    }



    @Test
    @DisplayName("카테고리 생성 테스트")
    public void createCategory() {

        CategoryFormDTO categoryFormDTO = new CategoryFormDTO();
        categoryFormDTO.setTitle("테스트 카테고리");
        categoryFormDTO.setDescription("카테고리 테스트입니다.");


        Category saveCate=categoryService.saveCategory(categoryFormDTO,saveUser());
        log.info(String.valueOf(saveCate));

    }

    @Test
    @DisplayName("카테고리 목록불러오기 테스트")
    public void loadAllCategory() {

        CategoryFormDTO categoryFormDTO = new CategoryFormDTO();
        categoryFormDTO.setTitle("테스트 카테고리");
        categoryFormDTO.setDescription("카테고리 테스트입니다.");


        categoryService.saveCategory(categoryFormDTO,saveUser());
        //List<Category> category= categoryService.getAllCategories();
        //log.info("카테고리 목록"+category.toString());

    }

    @Test
    @DisplayName("카테고리 아이디 참조 테스트")
    public void selectCategory() {

        CategoryFormDTO categoryFormDTO = new CategoryFormDTO();
        categoryFormDTO.setTitle("테스트 카테고리");
        categoryFormDTO.setDescription("카테고리 테스트입니다.");
        Category category1=categoryService.saveCategory(categoryFormDTO,saveUser());
        Category category2=categoryService.saveCategory(categoryFormDTO,saveUser2());

       Optional <Category> categorySelect = categoryService.selectCategory(category1.getId());
        log.info(String.valueOf(categorySelect));

    }

    @Test
    @DisplayName("카테고리 삭제")
    public void deleteCategory() {

        CategoryFormDTO categoryFormDTO = new CategoryFormDTO();
        categoryFormDTO.setTitle("테스트 카테고리");
        categoryFormDTO.setDescription("카테고리 테스트입니다.");


        Category saveCate=categoryService.saveCategory(categoryFormDTO,saveUser());
        log.info(String.valueOf("삭제전"+saveCate));

        categoryService.delCategory(saveCate.getId());

        log.info(String.valueOf("삭제 후 카테고리  : "+saveCate));

    }
}