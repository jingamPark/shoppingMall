package com.example.toyproject_shoppingmall.controller;

import com.example.toyproject_shoppingmall.dto.MainProductDTO;
import com.example.toyproject_shoppingmall.dto.ProductSearchDTO;
import com.example.toyproject_shoppingmall.entity.Product;
import com.example.toyproject_shoppingmall.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.Optional;

@Controller
@RequiredArgsConstructor

public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private final ProductService productService;




    @GetMapping(value = {"/", "/{page}"})
    public String main(ProductSearchDTO productSearchDTO, @PathVariable("page") Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.orElse(0), 6);

        Page<MainProductDTO> products = productService.getMainProductPage(productSearchDTO, pageable);
        log.info("main프로덕트의 제품 목록 : " + products.toString());

        model.addAttribute("productSearchDTO", productSearchDTO);
        model.addAttribute("products", products);
        model.addAttribute("maxPage", 5);

        return "main";
    }









}

