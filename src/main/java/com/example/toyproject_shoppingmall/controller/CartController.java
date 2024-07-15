package com.example.toyproject_shoppingmall.controller;


import com.example.toyproject_shoppingmall.dto.CartDetailDTO;
import com.example.toyproject_shoppingmall.dto.CartOrderDTO;
import com.example.toyproject_shoppingmall.dto.CartProductDTO;
import com.example.toyproject_shoppingmall.entity.CartProduct;
import com.example.toyproject_shoppingmall.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.security.PublicKey;
import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor

public class CartController {


    private final CartService cartService;



    @PostMapping(value = "/cart")
    public @ResponseBody ResponseEntity order(@RequestBody @Valid CartProductDTO cartProductDTO
            , BindingResult bindingResult, Principal principal) {

        log.info("유저 로그인 아이디 : "+principal.getName());
        log.info("제품아이디 : " + cartProductDTO.getProductId());
        log.info("장바구니 제품 수량 : " +cartProductDTO.getCount());


        if (bindingResult.hasErrors()) {
            StringBuilder sb = new StringBuilder();
            List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
            for (FieldError fieldError : fieldErrorList) {
                sb.append(fieldError.getDefaultMessage());
            }

            return new ResponseEntity<String>(sb.toString(), HttpStatus.BAD_REQUEST);
        }
        String loginId = principal.getName();
        Long cartProductId;

        try {
            cartProductId = cartService.addCart(cartProductDTO, loginId);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
    }

    @GetMapping(value = "/cart")
    public String orderHist(Principal principal, Model model) {
        List<CartDetailDTO> cartDetailDTOList =
                cartService.getCartList(principal.getName());
        model.addAttribute("cartProducts", cartDetailDTOList);
        return "cart/cartList";
    }

    @PutMapping(value = "/cartProduct/{cartProductId}")
    public @ResponseBody ResponseEntity updateCartProduct(@PathVariable("cartProductId") Long cartProductID,
                                                          int count, Principal principal) {

        if (count <= 0) {
            return new ResponseEntity<String>("최소 1개 이상 담아주세요", HttpStatus.BAD_REQUEST);
        }else if (!cartService.validateCartProduct(cartProductID, principal.getName())){
            return new ResponseEntity<String>("수정 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        cartService.updateCartProductCount(cartProductID,count);
        return new ResponseEntity<Long>(cartProductID,HttpStatus.OK);
    }

    @DeleteMapping(value = "/cartProduct/{cartProductId}")
    public @ResponseBody ResponseEntity deleteCartProduct(@PathVariable("cartProductId") Long cartProductId
    , Principal principal) {

        if (!cartService.validateCartProduct(cartProductId, principal.getName())) {
            return new ResponseEntity<String>("수정권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

       String cartProdName =  cartService.deleteCartProduct(cartProductId);

        //return new ResponseEntity<Long>(cartProductId, HttpStatus.OK);
        return new ResponseEntity<String>(cartProdName, HttpStatus.OK);
    }


    @PostMapping(value = "/cart/orders")
    public @ResponseBody ResponseEntity OrderCartProduct
            (@RequestBody CartOrderDTO cartOrderDTO, Principal principal) {

        List<CartOrderDTO> cartOrderDTOList = cartOrderDTO.getCartOrderDTOList();

        if (cartOrderDTOList == null || cartOrderDTOList.size() == 0) {
            return new ResponseEntity<String>("주문할 상품을 선택해주세요.", HttpStatus.FORBIDDEN);
        }

        for (CartOrderDTO cartOrder : cartOrderDTOList) {
            if (!cartService.validateCartProduct(cartOrder.getCartProductId(), principal.getName())) {
                return new ResponseEntity<String>("주문 권한이 없습니다.", HttpStatus.FORBIDDEN);
            }
        }
        Long orderId = null;
        try{
          orderId = cartService.orderCartProduct(cartOrderDTOList, principal.getName());
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
        log.info(orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);

    }


}
