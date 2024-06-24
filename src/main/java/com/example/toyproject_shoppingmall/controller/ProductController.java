package com.example.toyproject_shoppingmall.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    @GetMapping("/register")
    public String register() {
//        상품등록

        return "productForm";
    }

    @GetMapping("/list")
    public String list() {


        return "/products/list";
    }

    @GetMapping("/view")
    public String view() {


        return "/products/view";
    }







}
