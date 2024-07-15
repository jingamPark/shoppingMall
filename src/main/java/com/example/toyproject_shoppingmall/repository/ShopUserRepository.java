package com.example.toyproject_shoppingmall.repository;

import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.repository.search.ShopUserRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface ShopUserRepository extends JpaRepository<ShopUser,Long>, QuerydslPredicateExecutor<ShopUser>, ShopUserRepositoryCustom {

    ShopUser findByLoginId(String loginId);
    ShopUser findByEmail(String email);
    ShopUser findByEmailAndName(String email, String name);


    ShopUser findByLoginIdAndEmail(String loginId, String email);



}
