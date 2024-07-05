package com.example.toyproject_shoppingmall.repository;

import com.example.toyproject_shoppingmall.entity.Product;
import com.example.toyproject_shoppingmall.entity.ProductImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImgRepository extends JpaRepository<ProductImg,Long> {


    List<ProductImg> findByProductIdOrderByIdAsc(Long productId);

    ProductImg findByProductIdAndRepImgYn(Long productId, String repImgYn);

}
