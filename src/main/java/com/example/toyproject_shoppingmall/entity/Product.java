package com.example.toyproject_shoppingmall.entity;

import com.example.toyproject_shoppingmall.constant.ProdSellStatus;
import com.example.toyproject_shoppingmall.dto.ProductFormDTO;
import com.example.toyproject_shoppingmall.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@NoArgsConstructor
@Entity
@Getter
@Setter
@ToString
@Table(name = "product")
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;   //고유 참조번호

    @Column(nullable = false, length = 40)
    private String prodName; //제품명

    @Column(nullable = false)
    private int price; //가격

    @Column(nullable = false)
    private int stockNumber; //재고수량

    @Lob
    @Column(nullable = false)
    private String prodDetail; // 제품 설명


    @Enumerated(EnumType.STRING)
    private ProdSellStatus prodSellStatus; //제품 판매상태(판매중,매진)

    public Product(Long id, String prodName, int price, int stockNumber, String prodDetail, ProdSellStatus prodSellStatus) {

    }


    //상품 업데이트or수정 시  dto값을 받는 메소드 생성
    public void updateProduct(ProductFormDTO productFormDTO) {
        this.prodName = productFormDTO.getProdName();
        this.price = productFormDTO.getPrice();
        this.stockNumber = productFormDTO.getStockNumber();
        this.prodDetail = productFormDTO.getProdDetail();
        this.prodSellStatus = productFormDTO.getProdSellStatus();
    }


}
