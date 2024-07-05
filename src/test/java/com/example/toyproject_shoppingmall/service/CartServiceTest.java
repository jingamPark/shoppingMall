package com.example.toyproject_shoppingmall.service;

import com.example.toyproject_shoppingmall.constant.ProdSellStatus;
import com.example.toyproject_shoppingmall.dto.CartProductDTO;
import com.example.toyproject_shoppingmall.entity.CartProduct;
import com.example.toyproject_shoppingmall.entity.Product;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.repository.CartProductRepository;
import com.example.toyproject_shoppingmall.repository.CartRepository;
import com.example.toyproject_shoppingmall.repository.ProductRepository;
import com.example.toyproject_shoppingmall.repository.ShopUserRepository;
import groovy.util.logging.Log4j2;
import jakarta.persistence.EntityExistsException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
@Log4j2
class CartServiceTest {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ShopUserRepository shopUserRepository;
    @Autowired
    CartService cartService;
    @Autowired
    CartProductRepository cartProductRepository;

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
    @DisplayName("장바구니 테스트")
    public void addCart() {
        Product product = saveProduct();
        ShopUser shopUser = saveUser();

        CartProductDTO cartProductDTO =new CartProductDTO();
        cartProductDTO.setCount(5);
        cartProductDTO.setProductId(product.getId());

        Long cartProductId = cartService.addCart(cartProductDTO, shopUser.getLoginId());

        CartProduct cartProduct = cartProductRepository.findById(cartProductId).orElseThrow(EntityExistsException::new);

        assertEquals(product.getId(), cartProduct.getProduct().getId());
        assertEquals(cartProductDTO.getCount(), cartProduct.getCount());

    }



}