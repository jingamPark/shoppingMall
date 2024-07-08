package com.example.toyproject_shoppingmall.entity;


import com.example.toyproject_shoppingmall.constant.CategoryType;
import com.example.toyproject_shoppingmall.entity.base.BaseEntity;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@Entity
public class Category extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 40)
    private String name;

    private String description;

    @ManyToOne
    private Product productId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoryType categoryType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parentCategory;

    @OneToMany(mappedBy = "parentCategory")
    private Set<Category> children ;
}
