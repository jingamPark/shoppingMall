package com.example.toyproject_shoppingmall.controller;

import com.example.toyproject_shoppingmall.dto.CategoryFormDTO;
import com.example.toyproject_shoppingmall.service.CategoryService;
import com.example.toyproject_shoppingmall.service.ShopUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;



@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {


    private final CategoryService categoryService;
    private final ShopUserService shopUserService;

    @GetMapping("/category")
    public @ResponseBody ResponseEntity cateList() {

//        List<Category> categories = categoryService.getAllCategories();
//        categories.forEach(category -> System.out.println("Title: " + category.getTitle()));
//        System.out.println(categories);
//        model.addAttribute("categories", categories);

        List<CategoryFormDTO> categories = categoryService.getAllCategories();

        categories.forEach(category -> log.info(category));

        return  new ResponseEntity<List<CategoryFormDTO>>( categories, HttpStatus.OK);

    }

    @GetMapping("/list")
    public String categoryUpdate(Model model) {

        List<CategoryFormDTO> categories = categoryService.getAllCategories();

        model.addAttribute("categories",categories);


        return "/categories/categoryList";
    }


    @GetMapping(value = "/create")
    public String createCategory(Model model,CategoryFormDTO categoryFormDTO,Principal principal) {

        String loginId = principal.getName();

        model.addAttribute("loginId",loginId);
        model.addAttribute("categoryFormDTO",categoryFormDTO);


        return "/categories/categoryForm";
    }

    // 카테고리 생성
    @PostMapping("/create")
    public String createCategory(CategoryFormDTO categoryFormDTO, Principal principal) {

       categoryService.saveCategory(categoryFormDTO, principal.getName());

        return "redirect:/";
    }



    @GetMapping(value = "/{categoryId}")
    public String categoryDtl(@PathVariable("categoryId") Long categoryId, Model model) {

        log.info("제품 아이디는 가져옴?? >>>>"+categoryId);

        CategoryFormDTO categoryFormDTO = categoryService.selectCategory(categoryId);
       // log.info(categoryFormDTO);
        model.addAttribute("categoryFormDTO",categoryFormDTO);

        return "/categories/categoryForm";
    }

    @PostMapping(value = "/{categoryId}")
    public String updateCategory(CategoryFormDTO categoryFormDTO) {



        return "redirect:/categories/list";
    }

    @DeleteMapping(value = "/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        categoryService.delCategory(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}//class






