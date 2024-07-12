package com.example.toyproject_shoppingmall.controller;


import com.example.toyproject_shoppingmall.dto.OrderDTO;
import com.example.toyproject_shoppingmall.dto.OrderHistDTO;
import com.example.toyproject_shoppingmall.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@Log4j2
@RequiredArgsConstructor
public class OrderController {


    private final OrderService orderService;

    @PostMapping(value = "/order")
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


    @GetMapping(value = {"/orders", "/orders/{page}"})
    public String orderHist(@PathVariable("page") Optional<Integer> page,
                            Principal principal, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() :0,4);

        Page<OrderHistDTO> orderHistDTOList = orderService.getOrderList(principal.getName(),pageable);

        log.info("컨트롤러 로그인 아디디 확인 : " + principal.getName());
        log.info("컨트롤러 order리스트 확인 :  "+ orderHistDTOList.getContent());
        model.addAttribute("orders",orderHistDTOList);
        model.addAttribute("page",pageable.getPageNumber());
        model.addAttribute("maxPage",5);

        return "/order/orderHist";
    }

    @PostMapping("/order/{orderId}/cancel")
    public @ResponseBody ResponseEntity cancelOrder( @PathVariable("orderId") Long orderId
            , Principal principal) {

        String email = principal.getName();

        log.info("오더아이디 : " + orderId);
        if (!orderService.validateOrder(orderId, email)) {
            return new ResponseEntity<String>("주문취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        orderService.cancelOrder(orderId);

        return new ResponseEntity<Long>(orderId,HttpStatus.OK);
    }

    @PostMapping("/order/{orderId}/return")
    public @ResponseBody ResponseEntity returnOrder( @PathVariable("orderId") Long orderId
            , Principal principal) {

        String email = principal.getName();

        log.info("오더아이디 : " + orderId);
        if (!orderService.validateOrder(orderId, email)) {
            return new ResponseEntity<String>("주문취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        orderService.returnOder(orderId);

        return new ResponseEntity<Long>(orderId,HttpStatus.OK);
    }




}
