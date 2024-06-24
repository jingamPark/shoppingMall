package com.example.toyproject_shoppingmall.repository;

import com.example.toyproject_shoppingmall.entity.Product;
import com.example.toyproject_shoppingmall.repository.search.ProductRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface ProductRepository extends JpaRepository<Product,Long>, QuerydslPredicateExecutor<Product>, ProductRepositoryCustom {






}
