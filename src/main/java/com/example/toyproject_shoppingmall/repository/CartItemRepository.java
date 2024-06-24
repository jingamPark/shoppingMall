package com.example.toyproject_shoppingmall.repository;

import com.example.toyproject_shoppingmall.entity.Cart;
import com.example.toyproject_shoppingmall.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Long> {






}
