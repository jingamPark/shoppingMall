package com.example.toyproject_shoppingmall.service;


import com.example.toyproject_shoppingmall.dto.OrderDTO;
import com.example.toyproject_shoppingmall.dto.ProductFormDTO;
import com.example.toyproject_shoppingmall.entity.Order;
import com.example.toyproject_shoppingmall.entity.OrderProduct;
import com.example.toyproject_shoppingmall.entity.Product;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.repository.OrderRepository;
import com.example.toyproject_shoppingmall.repository.ProductRepository;
import com.example.toyproject_shoppingmall.repository.ShopUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final ShopUserRepository shopUserRepository;
    private final OrderRepository orderRepository;


    public Long order(OrderDTO orderDTO, String loginId) {
        Product product = productRepository.findById(orderDTO.getProductId())
                .orElseThrow(EntityNotFoundException::new);
        ShopUser shopUser = shopUserRepository.findByLoginId(loginId);

        List<OrderProduct> orderProductList = new ArrayList<>();
        OrderProduct orderProduct = OrderProduct
                .createOrderProduct(product, orderDTO.getCount());
        orderProductList.add(orderProduct);






        Order order = Order.createOrder(shopUser, orderProductList);
        orderRepository.save(order);

        return order.getId();
    }


}
