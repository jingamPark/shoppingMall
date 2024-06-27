package com.example.toyproject_shoppingmall.repository;

import com.example.toyproject_shoppingmall.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderProduct,Long> {






}
