package com.example.toyproject_shoppingmall.controller;

import com.example.toyproject_shoppingmall.dto.CategoryFormDTO;
import com.example.toyproject_shoppingmall.entity.Category;
import com.example.toyproject_shoppingmall.service.CategoryService;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.apache.catalina.LifecycleState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {


    private final CategoryService categoryService;

    @GetMapping("/categoryList")
    public @ResponseBody ResponseEntity cateList() {

//        List<Category> categories = categoryService.getAllCategories();
//        categories.forEach(category -> System.out.println("Title: " + category.getTitle()));
//        System.out.println(categories);
//        model.addAttribute("categories", categories);

        List<CategoryFormDTO> categories = categoryService.getAllCategories();

        categories.forEach(category -> log.info(category));

        return  new ResponseEntity<List<CategoryFormDTO>>( categories, HttpStatus.OK);

    }



    @GetMapping("/header")
    public void cate(Model model) {

//        List<Category> categories = categoryService.getAllCategories();
//        categories.forEach(category -> System.out.println("Title: " + category.getTitle()));
//        System.out.println(categories);
//        model.addAttribute("categories", categories);

    }



}//class






