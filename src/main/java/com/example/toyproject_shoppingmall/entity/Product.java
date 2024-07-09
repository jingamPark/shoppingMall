package com.example.toyproject_shoppingmall.entity;

import com.example.toyproject_shoppingmall.constant.CategoryType;
import com.example.toyproject_shoppingmall.constant.ProdSellStatus;
import com.example.toyproject_shoppingmall.dto.ProductFormDTO;
import com.example.toyproject_shoppingmall.entity.base.BaseEntity;
import com.example.toyproject_shoppingmall.exception.OutOfStockException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


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

    @Enumerated(EnumType.STRING)
    private CategoryType categoryType;




    //상품 업데이트or수정 시  dto값을 받는 메소드 생성
    public void updateProduct(ProductFormDTO productFormDTO) {

        this.prodName = productFormDTO.getProdName();
        this.price = productFormDTO.getPrice();
        this.stockNumber = productFormDTO.getStockNumber();
        this.prodDetail = productFormDTO.getProdDetail();
        this.prodSellStatus = productFormDTO.getProdSellStatus();
        this.categoryType = productFormDTO.getCategoryType();
    }

    //상품 재고 감소
    public void removeStock(int stockNumber) {
        int restStock =this.stockNumber - stockNumber;



        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족 합니다. " +
                    "(현재 재고 수량 : " + this.stockNumber+ ")");


        }



        this.stockNumber = restStock;
    }


    //상푸 재고증가
    public void addStock(int stockNumber) {
        this.stockNumber += stockNumber;
    }




}
