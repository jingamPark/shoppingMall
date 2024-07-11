package com.example.toyproject_shoppingmall.controller;


import com.example.toyproject_shoppingmall.dto.ProductFormDTO;
import com.example.toyproject_shoppingmall.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;



    @GetMapping("/view/{productId}")
    public String view(Model model, @PathVariable("productId") Long productId) {

        ProductFormDTO productFormDTO = productService.getProductDtl(productId);
        model.addAttribute("product", productFormDTO);

        return "/products/view";
    }






}
