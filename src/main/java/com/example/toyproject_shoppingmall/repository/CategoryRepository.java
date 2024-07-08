package com.example.toyproject_shoppingmall.repository;

import com.example.toyproject_shoppingmall.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
