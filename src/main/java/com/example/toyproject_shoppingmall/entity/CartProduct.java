package com.example.toyproject_shoppingmall.entity;

import com.example.toyproject_shoppingmall.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cart_product")
@Getter
@Setter
@ToString
public class CartProduct extends BaseEntity {

    @Id
    @Column(name = "cart_product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;        //코드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;      // 장바구니

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;    //아이템

    private int count; //수량 장바구니 제품당 담는 수량




}
