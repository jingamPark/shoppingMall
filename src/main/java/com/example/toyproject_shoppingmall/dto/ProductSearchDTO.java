package com.example.toyproject_shoppingmall.dto;


import com.example.toyproject_shoppingmall.constant.ProdSellStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@ToString
public class ProductSearchDTO {

    private String searchDateType;

    private ProdSellStatus searchSellStatus;

    private String searchBy;

    private String searchQuery = "";


}
