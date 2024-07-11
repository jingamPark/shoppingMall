package com.example.toyproject_shoppingmall.controller;


import com.example.toyproject_shoppingmall.dto.MainProductDTO;
import com.example.toyproject_shoppingmall.dto.ProductFormDTO;
import com.example.toyproject_shoppingmall.dto.ProductSearchDTO;
import com.example.toyproject_shoppingmall.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;
import java.util.Optional;

@Controller

@RequiredArgsConstructor

public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private final ProductService productService;


    @GetMapping(value = {"/","/{page}"})
    public String main(ProductSearchDTO productSearchDTO
            , @PathVariable Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() :0 ,4);

        Page<MainProductDTO> products = productService
                .getMainProductPage(productSearchDTO, pageable);
        log.info("main프로덕트의 제품 목록 : "+products.toString());



        model.addAttribute("productSearchDTO", productSearchDTO);
        log.info("페이지 정보 ??? : "+products.toString());
        model.addAttribute("products", products);

        model.addAttribute("maxPage", 5);



        return "main";
    }


//    @GetMapping
//    public String listByCategory(Model model, @RequestParam("category") String category) {
//
//        log.info("카테고리: " + category);
//        List<ProductFormDTO> products = productService.getProductsByCategory(category);
//        log.info("제품 정보 : " + products);
//        model.addAttribute("products", products);
//        model.addAttribute("category", category);
//        return "main"+category;
//    }




}

