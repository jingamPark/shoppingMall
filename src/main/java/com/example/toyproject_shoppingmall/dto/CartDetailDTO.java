package com.example.toyproject_shoppingmall.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class CartDetailDTO {

    private Long cartProductId;
    private String prodName;
    private int price;
    private int count;
    private String imgUrl;

    public CartDetailDTO(Long cartProductId, String prodName, int price,
                         int count, String imgUrl) {
        this.cartProductId = cartProductId;
        this.prodName = prodName;
        this.price = price;
        this.count = count;
        this.imgUrl = imgUrl;

    }

}
