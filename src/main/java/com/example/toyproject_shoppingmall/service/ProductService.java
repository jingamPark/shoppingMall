package com.example.toyproject_shoppingmall.service;

import com.example.toyproject_shoppingmall.dto.MainProductDTO;
import com.example.toyproject_shoppingmall.dto.ProductFormDTO;
import com.example.toyproject_shoppingmall.dto.ProductImgDTO;
import com.example.toyproject_shoppingmall.dto.ProductSearchDTO;
import com.example.toyproject_shoppingmall.entity.Category;
import com.example.toyproject_shoppingmall.entity.Product;
import com.example.toyproject_shoppingmall.entity.ProductImg;
import com.example.toyproject_shoppingmall.repository.ProductImgRepository;
import com.example.toyproject_shoppingmall.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
@Transactional
public class ProductService {

    private final ProductImgRepository productImgRepository;
    private final ProductImgService productImgService;
    private final ProductRepository productRepository;


    public Long saveProduct(ProductFormDTO productFormDTO,
                            List<MultipartFile> productImgFileList) throws Exception {
        //상품 등록
        Product product = productFormDTO.createProduct();
        productRepository.save(product);

        for (int i = 0; i < productImgFileList.size(); i++) {
            ProductImg productImg = new ProductImg();
            productImg.setProduct(product);
            if (i == 0) {
                productImg.setRepImgYn("Y");
            } else {
                productImg.setRepImgYn("N");
            }
            productImgService.saveProductImg(productImg, productImgFileList.get(i));


        }
        return product.getId();
    }

    @Transactional(readOnly = true)
    public ProductFormDTO getProductDtl(Long productId) {

        List<ProductImg> productImgList =
                productImgRepository.findByProductIdOrderByIdAsc(productId);

        List<ProductImgDTO> productImgDTOList = new ArrayList<>();
        for (ProductImg productImg : productImgList) {
            ProductImgDTO productImgDTO = ProductImgDTO.of(productImg);
            productImgDTOList.add(productImgDTO);
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);
        ProductFormDTO productFormDTO = ProductFormDTO.of(product);
        productFormDTO.setProductImgDTOList(productImgDTOList);


        return productFormDTO;

    }

    public Long updateProduct(ProductFormDTO productFormDTO,
                              List<MultipartFile> productImgFileList) throws Exception {

        Product product = productRepository.findById(productFormDTO.getId())
                .orElseThrow(EntityNotFoundException::new);
        log.info("상품정보는 가져옴??????" + product);

        product.updateProduct(productFormDTO);

        List<Long> prodImgIds = productFormDTO.getProductImgIds();

        //아이템 이미지 파일 수량? 조회
        for (int i = 0; i < productImgFileList.size(); i++) {
            productImgService.updateProductImg(prodImgIds.get(i), productImgFileList.get(i));
        }

        return product.getId();
    }

    @Transactional(readOnly = true)
    public Page<Product> getAdminProductPage(ProductSearchDTO productSearchDTO, Pageable pageable) {

        return productRepository.getAdminProductPage(productSearchDTO, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainProductDTO> getMainProductPage(ProductSearchDTO productSearchDTO, Pageable pageable) {
        return productRepository.getMainProductPage(productSearchDTO,pageable);
    }

    @Transactional(readOnly = true)
    public List<ProductFormDTO> getProductsByCategory(String categoryTitle) {
        List<Product> products = productRepository.findByCategoryTitle(categoryTitle);
        List<ProductFormDTO> productFormDTOList = new ArrayList<>();
        for (Product product : products) {
            productFormDTOList.add(ProductFormDTO.of(product));
        }
        return productFormDTOList;
    }

}//class
