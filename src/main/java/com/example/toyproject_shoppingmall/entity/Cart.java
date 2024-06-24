package com.example.toyproject_shoppingmall.entity;


import com.example.toyproject_shoppingmall.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity // jpa에서 관리를 할수 있습니다. 엔티티매니저
@Table(name = "cart") //jpa를 이용할 댸 자동으로 데이터베이스 설정과 데이터베이스 내 테이블을 같이 확인하기 댸문에 에러 나올 수 있음
@Getter
@Setter
@ToString
public class Cart extends BaseEntity {

    @Id
    @Column(name = "cart_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //코드

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_user_id")
    private  ShopUser shopUser; //상품명



}
