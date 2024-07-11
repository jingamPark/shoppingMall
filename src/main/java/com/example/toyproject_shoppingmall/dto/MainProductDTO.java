package com.example.toyproject_shoppingmall.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.repository.Query;

@Getter
@Setter
@ToString
public class MainProductDTO {

    private Long id;

    private String prodName;

    private String prodDetail;

    private String imgUrl;

    private Integer price;

    private String category;

    @QueryProjection
    public MainProductDTO(Long id,String prodName, String prodDetail, String imgUrl, Integer price) {
        this.id = id;
        this.prodName = prodName;
        this.prodDetail = prodDetail;
        this.imgUrl = imgUrl;
        this.price = price;

    }

}
