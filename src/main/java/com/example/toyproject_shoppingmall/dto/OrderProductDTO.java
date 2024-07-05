package com.example.toyproject_shoppingmall.dto;

import com.example.toyproject_shoppingmall.entity.OrderProduct;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderProductDTO {

    private String prodName;

    private int count;

    private int orderPrice;

    private String imgUrl;



    public OrderProductDTO(OrderProduct orderProduct,String imgUrl) {
        this.prodName = orderProduct.getProduct().getProdName();
        this.count = orderProduct.getCount();
        this.orderPrice = orderProduct.getOrderPrice();
        this.imgUrl = imgUrl;
    }



}
