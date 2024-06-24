package com.example.toyproject_shoppingmall.dto;


import com.example.toyproject_shoppingmall.constant.ProdSellStatus;
import com.example.toyproject_shoppingmall.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString
public class ProductFormDTO {

    private static ModelMapper modelMapper =new ModelMapper();


    private Long id;   //고유 참조번호
    @NotBlank(message = "상품명은 필수 입력 값 입니다.")
    private String prodName; //제품명
    @NotNull(message = "가격은 필수 입력 값 입니다.")
    private int price; //가격
    @NotBlank(message = "상품 설명은 필수 입력 값 입니다.")
    private String prodDetail; // 제품 설명
    @NotNull(message = "재고는 필수 입력 값 입니다.")
    private int stockNumber; //재고수량

    private ProdSellStatus prodSellStatus;

    private List<ProductImgDTO> productImgDTOList =new ArrayList<>();

    private List<Long> productImgIds = new ArrayList<>(); //이미지의 아이디 리스트


    public Product createProduct() {
        return modelMapper.map(this, Product.class);
    }

    public static ProductFormDTO of(Product product) {
        return modelMapper.map(product, ProductFormDTO.class);
    }

}
