package com.example.toyproject_shoppingmall.service;

import com.example.toyproject_shoppingmall.constant.OrderStatus;
import com.example.toyproject_shoppingmall.constant.ProdSellStatus;
import com.example.toyproject_shoppingmall.dto.OrderDTO;
import com.example.toyproject_shoppingmall.entity.Order;
import com.example.toyproject_shoppingmall.entity.OrderProduct;
import com.example.toyproject_shoppingmall.entity.Product;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.repository.OrderRepository;
import com.example.toyproject_shoppingmall.repository.ProductRepository;
import com.example.toyproject_shoppingmall.repository.ShopUserRepository;
import groovy.util.logging.Log;
import groovy.util.logging.Log4j2;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@Log4j2
class OrderServiceTest {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceTest.class);
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ShopUserRepository shopUserRepository;

    public Product saveProduct() {

        Product product = new Product();

        product.setProdName("test상품");
        product.setPrice(100000);
        product.setProdDetail("테스트 상품 상세설명 입니다.");
        product.setProdSellStatus(ProdSellStatus.SELL);
        product.setStockNumber(100);

        return productRepository.save(product);

    }

    public ShopUser saveUser() {
        ShopUser shopUser = new ShopUser();
        shopUser.setName("길동");
        shopUser.setLoginId("test");
        shopUser.setEmail("a@a.a");
        shopUser.setPassword("12341234");
        shopUser.setTel("010-1111-2222");
        shopUser.setAddress("부천시");
        return shopUserRepository.save(shopUser);
    }

    @Test
    @DisplayName("주문테스트입니다.")
    public void order() {
        Product product = saveProduct();
        ShopUser shopUser = saveUser();


        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setCount(10);
        orderDTO.setProductId(product.getId());

        Long orderId = orderService.order(orderDTO, shopUser.getLoginId());

        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);

        List<OrderProduct> productList = order.getOrderProducts();

        int totalPrice = orderDTO.getCount()*product.getPrice();

        assertEquals(totalPrice, order.getTotalPrice());

        log.info("Order DTO: {}", orderDTO);
        log.info("Retrieved order: {}", order);
        log.info("Order products: {}", productList);
        log.info("Expected total price: {}", totalPrice);
        log.info("Actual total price: {}", order.getTotalPrice());

    }

    @Test
    @DisplayName("주문 취소 테스트")
    public void cancelOrderTest() {

        Product product = saveProduct();
        ShopUser shopUser = saveUser();
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setCount(10);
        orderDTO.setProductId(product.getId());
        Long orderId= orderService.order(orderDTO, shopUser.getLoginId());

        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        orderService.cancelOrder(orderId);
        assertEquals(OrderStatus.CANCEL, order.getOrderStatus());
        assertEquals(100, product.getStockNumber());

        log.info(String.valueOf(order.getOrderStatus()));
        log.info(String.valueOf(product.getStockNumber()));


    }


}