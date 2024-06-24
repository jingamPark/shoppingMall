package com.example.toyproject_shoppingmall.entity;


import com.example.toyproject_shoppingmall.constant.OrderStatus;
import com.example.toyproject_shoppingmall.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity // jpa에서 관리를 할수 있습니다. 엔티티매니저
@Table(name = "orders") //jpa를 이용할 댸 자동으로 데이터베이스 설정과 데이터베이스 내 테이블을 같이 확인하기 댸문에 에러 나올 수 있음
@Getter
@Setter

public class Order extends BaseEntity {
    @Id
    @Column(name = "orders_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopUser_id")
    private ShopUser shopUser;      // 장바구니

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL, orphanRemoval = true
            , fetch = FetchType.LAZY)
    private List<OrderItem> orderItemList =new ArrayList<>();



    private LocalDateTime orderDate;

    //주문 취소 , 주문
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;







}
