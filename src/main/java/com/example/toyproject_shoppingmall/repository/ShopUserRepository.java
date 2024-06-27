package com.example.toyproject_shoppingmall.repository;

import com.example.toyproject_shoppingmall.entity.ShopUser;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ShopUserRepository extends JpaRepository<ShopUser,Long> {

    ShopUser findByLoginId(String loginId);
    ShopUser findByEmail(String email);
    ShopUser findByEmailAndName(String email, String name);

    ShopUser findByLoginIdAndEmail(String password, String email);





}
