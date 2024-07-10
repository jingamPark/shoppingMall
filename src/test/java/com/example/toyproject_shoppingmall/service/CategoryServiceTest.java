package com.example.toyproject_shoppingmall.service;

import com.example.toyproject_shoppingmall.dto.CategoryFormDTO;
import com.example.toyproject_shoppingmall.entity.Category;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.repository.CategoryRepository;
import com.example.toyproject_shoppingmall.repository.ShopUserRepository;


import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;



@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@Log4j2

class CategoryServiceTest {

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

        Category category =categoryService.saveCategory(categoryFormDTO,saveUser().getLoginId());
        log.info(category);

    }

//    @Test
//    @DisplayName("카테고리 목록불러오기 테스트")
//    public void loadAllCategory() {
//        Long id = null;
//        String title= "";
//        String description = "";
//        CategoryFormDTO categoryFormDTO = new CategoryFormDTO(id,title, description);
//        categoryFormDTO.setTitle("테스트 카테고리");
//        categoryFormDTO.setDescription("카테고리 테스트입니다.");
//
//
//        //categoryService.saveCategory(categoryFormDTO,saveUser());
//        //List<Category> category= categoryService.getAllCategories();
//        //log.info("카테고리 목록"+category.toString());
//
//    }
//
//    @Test
//    @DisplayName("카테고리 아이디 참조 테스트")
//    public void selectCategory() {
//        Long id = null;
//        String title= "";
//        String description = "";
//        CategoryFormDTO categoryFormDTO = new CategoryFormDTO(id,title, description);
//        categoryFormDTO.setTitle("테스트 카테고리");
//        categoryFormDTO.setDescription("카테고리 테스트입니다.");
//
//
//
//
//
//
//    }
//
//    @Test
//    @DisplayName("카테고리 삭제")
//    public void deleteCategory() {
//        Long id = null;
//        String title= "";
//        String description = "";
//        CategoryFormDTO categoryFormDTO = new CategoryFormDTO(id,title, description);
//        categoryFormDTO.setTitle("테스트 카테고리");
//        categoryFormDTO.setDescription("카테고리 테스트입니다.");
//
//
//
//
//
//
//
//
//
//    }
}