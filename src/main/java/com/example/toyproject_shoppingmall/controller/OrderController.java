package com.example.toyproject_shoppingmall.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {





    @GetMapping("/view")
    public String view() {


        return "/orders/view";
    }



    @GetMapping("/list")
    public String list() {


        return "/orders/list";
    }



}
