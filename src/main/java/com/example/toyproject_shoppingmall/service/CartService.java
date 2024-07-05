package com.example.toyproject_shoppingmall.service;


import com.example.toyproject_shoppingmall.dto.CartDetailDTO;
import com.example.toyproject_shoppingmall.dto.CartOrderDTO;
import com.example.toyproject_shoppingmall.dto.CartProductDTO;
import com.example.toyproject_shoppingmall.dto.OrderDTO;
import com.example.toyproject_shoppingmall.entity.Cart;
import com.example.toyproject_shoppingmall.entity.CartProduct;
import com.example.toyproject_shoppingmall.entity.Product;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.repository.CartProductRepository;
import com.example.toyproject_shoppingmall.repository.CartRepository;
import com.example.toyproject_shoppingmall.repository.ProductRepository;
import com.example.toyproject_shoppingmall.repository.ShopUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
    private final ProductRepository productRepository;
    private final ShopUserRepository shopUserRepository;
    private final CartRepository cartRepository;
    private final CartProductRepository cartProductRepository;
    private final OrderService orderService;

    public Long addCart(CartProductDTO cartProductDTO, String loginId) {

        Product product = productRepository.findById(cartProductDTO.getProductId())
                .orElseThrow(EntityNotFoundException::new);


        ShopUser shopUser = shopUserRepository.findByLoginId(loginId);

        Cart cart = cartRepository.findByShopUserId(shopUser.getId());

        if (cart == null) {
            cart = Cart.createCart(shopUser);
            cartRepository.save(cart);
        }
        CartProduct savedCartProduct = cartProductRepository
                .findByCartIdAndProductId(cart.getId(),product.getId());

        if (savedCartProduct != null) {
            savedCartProduct.addCount(cartProductDTO.getCount());
            return savedCartProduct.getId();
        } else {
            CartProduct cartProduct =
                    CartProduct.createCartProduct(cart, product, cartProductDTO.getCount());
            cartProductRepository.save(cartProduct);
            return cartProduct.getId();
        }

    }


    @Transactional(readOnly = true)
    public List<CartDetailDTO> getCartList(String loginId) {

        List<CartDetailDTO> cartDetailDTOList = new ArrayList<>();

        ShopUser shopUser = shopUserRepository.findByLoginId(loginId);
        Cart cart = cartRepository.findByShopUserId(shopUser.getId());
        if (cart == null) {
            return cartDetailDTOList;
        }
        cartDetailDTOList = cartProductRepository.findCartDetailDTOList(cart.getId());

        return cartDetailDTOList;
    }


    @Transactional(readOnly = true)
    public boolean validateCartProduct(Long cartProductId, String loginId) {
        ShopUser curShopuser = shopUserRepository.findByLoginId(loginId);
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new);
        ShopUser savedShopuser =cartProduct.getCart().getShopUser();

        if (!StringUtils.equals(curShopuser.getLoginId(), savedShopuser.getLoginId())) {
            return false;
        }
        return true;
    }

    public void updateCartProductCount(Long cartProductId, int count) {
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new);
        cartProduct.updateCount(count);
    }

    public void deleteCartProduct(Long cartProductId) {
        CartProduct cartProduct = cartProductRepository.findById(cartProductId)
                .orElseThrow(EntityNotFoundException::new);
        cartProductRepository.delete(cartProduct);

    }

    public Long orderCartProduct(List<CartOrderDTO> cartOrderDTOList, String loginId) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (CartOrderDTO cartOrderDTO : cartOrderDTOList) {
            CartProduct cartProduct = cartProductRepository
                    .findById(cartOrderDTO.getCartProductId())
                    .orElseThrow(EntityNotFoundException::new);

            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setProductId(cartProduct.getProduct().getId());
            orderDTO.setCount(cartProduct.getCount());
            orderDTOList.add(orderDTO);
        }

        Long orderId = orderService.orders(orderDTOList, loginId);

        for (CartOrderDTO cartOrderDTO : cartOrderDTOList) {
            CartProduct cartProduct =cartProductRepository
                    .findById(cartOrderDTO.getCartProductId())
                    .orElseThrow(EntityNotFoundException::new);
            cartProductRepository.delete(cartProduct);

        }
        return orderId;
    }























}//class
