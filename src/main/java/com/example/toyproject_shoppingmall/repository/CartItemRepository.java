package com.example.toyproject_shoppingmall.repository;

import com.example.toyproject_shoppingmall.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartProduct,Long> {






}
