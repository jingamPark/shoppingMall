package com.example.toyproject_shoppingmall.service;


import com.example.toyproject_shoppingmall.dto.OrderDTO;
import com.example.toyproject_shoppingmall.dto.OrderHistDTO;

import com.example.toyproject_shoppingmall.dto.OrderProductDTO;
import com.example.toyproject_shoppingmall.entity.*;
import com.example.toyproject_shoppingmall.repository.OrderRepository;
import com.example.toyproject_shoppingmall.repository.ProductImgRepository;
import com.example.toyproject_shoppingmall.repository.ProductRepository;
import com.example.toyproject_shoppingmall.repository.ShopUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final ShopUserRepository shopUserRepository;
    private final OrderRepository orderRepository;
    private final ProductImgRepository productImgRepository;




    @Transactional(readOnly = true)
    public Page<OrderHistDTO> getOrderList(String loginId, Pageable pageable) {

        List<Order>orders = orderRepository.findOrders(loginId,pageable);
        Long totalCount = orderRepository.countOrder(loginId);

        List<OrderHistDTO> orderHistDTOS = new ArrayList<>();

        for (Order order : orders) {
            OrderHistDTO orderHistDTO = new OrderHistDTO(order);
            List<OrderProduct> orderProducts =order.getOrderProducts();

            for (OrderProduct orderProduct : orderProducts) {
                ProductImg productImg
                        = productImgRepository.findByProductIdAndRepImgYn(orderProduct.getProduct().getId(),"Y");
                OrderProductDTO orderProductDTO = new OrderProductDTO(orderProduct, productImg.getImgUrl());
                orderHistDTO.addOrderProductDTO(orderProductDTO);

            }
            orderHistDTOS.add(orderHistDTO);
        }
        return new PageImpl<OrderHistDTO>(orderHistDTOS,pageable,totalCount);

    }



    public Long order(OrderDTO orderDTO, String loginId) {

        Product product = productRepository
                .findById(orderDTO.getProductId())
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

    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String loginId) {
        ShopUser curShopuser = shopUserRepository.findByLoginId(loginId);
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        ShopUser savedShopuser = order.getShopUser();

        if (!StringUtils.equals(curShopuser.getLoginId(), savedShopuser.getLoginId())) {
            return false;
        }
        return true;

    }



    public Long orders(List<OrderDTO> orderDTOList, String loginId) {

        ShopUser shopUser = shopUserRepository.findByLoginId(loginId);
        List<OrderProduct> orderProductList = new ArrayList<>();

        for (OrderDTO orderDTO : orderDTOList) {
            Product product = productRepository.findById(orderDTO.getProductId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderProduct orderProduct =
                    OrderProduct.createOrderProduct(product, orderDTO.getCount());
            orderProductList.add(orderProduct);
        }

        Order order = Order.createOrder(shopUser, orderProductList);
        orderRepository.save(order);
        return order.getId();
    }

    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();

    }

    public void returnOder(Long orderId) {
        Order order =orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        order.returnOder();
    }

}
