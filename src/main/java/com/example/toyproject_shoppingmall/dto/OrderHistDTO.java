package com.example.toyproject_shoppingmall.dto;

import com.example.toyproject_shoppingmall.constant.OrderStatus;
import com.example.toyproject_shoppingmall.entity.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;



@Getter
@Setter
@ToString
public class OrderHistDTO {


   // 주문 상태

    public OrderHistDTO(Order order) {
        this.orderId = order.getId();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        this.orderStatus = order.getOrderStatus();

    }

    private Long orderId; // 주문 아이디

    private String orderDate; // 주문 날짜

    private OrderStatus orderStatus;



    private List<OrderProductDTO> orderProductDTOList = new ArrayList<>();

    public void addOrderProductDTO(OrderProductDTO orderProductDTO) {
        orderProductDTOList.add(orderProductDTO);
    }


}
