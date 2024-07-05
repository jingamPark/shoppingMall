package com.example.toyproject_shoppingmall.repository;

import com.example.toyproject_shoppingmall.dto.CartDetailDTO;
import com.example.toyproject_shoppingmall.entity.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CartProductRepository extends JpaRepository<CartProduct,Long> {

    CartProduct findByCartIdAndProductId(Long cartId, Long productId);


    @Query("select new com.example.toyproject_shoppingmall.dto.CartDetailDTO" +
            "(cp.id, p.prodName, p.price, cp.count, pm.imgUrl) " +
            "from CartProduct cp , ProductImg pm " +
            "join cp.product p " +
            "where cp.cart.id = :cartId " +
            "and pm.product.id = cp.product.id " +
            "and pm.repImgYn = 'Y' " +
            "order by cp.regTime desc ")
    List<CartDetailDTO> findCartDetailDTOList(Long cartId);


}
