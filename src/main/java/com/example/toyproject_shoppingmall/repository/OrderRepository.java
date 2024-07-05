package com.example.toyproject_shoppingmall.repository;

import com.example.toyproject_shoppingmall.entity.Order;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    @Query("select o from  Order o " +
            "where o.shopUser.loginId = :loginId" +
            " order by o.orderDate desc ")
    List<Order> findOrders(@Param("loginId") String loginId, Pageable pageable);
    //아이디에 맞는 주문 불러오기


    @Query("select count(o) from Order o where o.shopUser.loginId = :loginId")
    Long countOrder(@Param("loginId") String loginId);
    //아이디에 주문개수 확인

}
