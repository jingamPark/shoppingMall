package com.example.toyproject_shoppingmall.controller;


import com.example.toyproject_shoppingmall.dto.OrderDTO;
import com.example.toyproject_shoppingmall.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {


    private final OrderService orderService;

    @PostMapping(value = "/add")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid OrderDTO orderDTO,
                                              BindingResult bindingResult, Principal principal) {

        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrors) {
                sb.append(fieldError.getDefaultMessage());
            }
            return new ResponseEntity<String>(sb.toString(),HttpStatus.BAD_REQUEST);
        }
        String loginId = principal.getName();
        Long orderId;

        try {
            orderId = orderService.order(orderDTO, loginId);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }

//    @PostMapping("/add")
//    public @ResponseBody ResponseEntity addOrder(@RequestBody @Valid OrderDTO orderDTO) {
//
//        return ResponseEntity.ok().body("데이터가 오긴온다");
//    }







    @GetMapping("/view/")
    public String view() {


        return "/orders/view";
    }



    @GetMapping("/list")
    public String list() {


        return "/orders/list";
    }



}
