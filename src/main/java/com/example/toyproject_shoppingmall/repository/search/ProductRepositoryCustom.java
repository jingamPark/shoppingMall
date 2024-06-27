package com.example.toyproject_shoppingmall.repository.search;

import com.example.toyproject_shoppingmall.dto.MainProductDTO;
import com.example.toyproject_shoppingmall.dto.ProductImgDTO;
import com.example.toyproject_shoppingmall.dto.ProductSearchDTO;
import com.example.toyproject_shoppingmall.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductRepositoryCustom {

    Page<Product> getAdminProductPage(ProductSearchDTO productSearchDTO, Pageable pageable);

    Page<MainProductDTO> getMainProductPage(ProductSearchDTO productSearchDTO, Pageable pageable);


}
