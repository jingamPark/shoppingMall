package com.example.toyproject_shoppingmall.entity;


import com.example.toyproject_shoppingmall.dto.CategoryFormDTO;
import com.example.toyproject_shoppingmall.entity.base.BaseEntity;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
@Entity
@ToString
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, length = 40)
    private String title;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopuser_id", nullable = false)
    private ShopUser shopUser;  // 유저의 아이디 참조



    public void updateCategory(CategoryFormDTO categoryFormDTO, ShopUser shopUser) {

        this.title = categoryFormDTO.getTitle();
        this.description = categoryFormDTO.getDescription();
        this.shopUser = shopUser;

    }




}
