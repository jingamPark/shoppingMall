package com.example.toyproject_shoppingmall.dto;


import com.example.toyproject_shoppingmall.entity.ProductImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ProductImgDTO {

    private static ModelMapper modelMapper =new ModelMapper();

    private Long id;

    private String imgName; //이미지 파일명

    private String oriImgName; //원본 이미지 파일명

    private String imgUrl; //이미지 조회 경로

    private String repImgYn; //대표 이미지 여부


    //entity 변환
    public static ProductImgDTO of(ProductImg productImg) {
        return modelMapper.map(productImg, ProductImgDTO.class);
    }


}
