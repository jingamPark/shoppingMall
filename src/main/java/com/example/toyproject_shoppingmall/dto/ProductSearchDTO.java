package com.example.toyproject_shoppingmall.dto;


import com.example.toyproject_shoppingmall.constant.ProdSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class ProductSearchDTO {

    private String searchDateType;

    private ProdSellStatus searchSellStatus;

    private String searchBy;

    private String searchQuery = "";


}
