package com.example.toyproject_shoppingmall.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CartOrderDTO {
    private Long cartProductId;
    private List<CartOrderDTO> cartOrderDTOList;



}
