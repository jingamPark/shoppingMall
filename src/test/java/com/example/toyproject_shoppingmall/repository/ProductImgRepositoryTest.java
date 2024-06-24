package com.example.toyproject_shoppingmall.repository;

import com.example.toyproject_shoppingmall.constant.ProdSellStatus;
import com.example.toyproject_shoppingmall.dto.ProductFormDTO;
import com.example.toyproject_shoppingmall.entity.Product;
import com.example.toyproject_shoppingmall.entity.ProductImg;
import com.example.toyproject_shoppingmall.service.ProductImgService;
import com.example.toyproject_shoppingmall.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.Value;
import lombok.With;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@TestPropertySource(locations = "classpath:application-test.properties")
class ProductImgRepositoryTest {




    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductImgRepository productImgRepository;
    @Autowired
    private ProductImgService productImgService;

    List<MultipartFile> createMultipartFileList() throws Exception {


        List<MultipartFile> multipartFileList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            String path = "C:/shopping/product";
            String imageName = "images" + i + ".jpag";
            MockMultipartFile  mockMultipartFile =
                    new MockMultipartFile(path,imageName,"image/jpa", new byte[]{1,2,3,4});
            multipartFileList.add(mockMultipartFile);

        }

        return multipartFileList;
    }

    @Test
    @DisplayName("상품 등록 테스트")
    @WithMockUser(username = "admin", roles = "ADMIN")
    void saveproduct()throws Exception {
        ProductFormDTO productFormDTO = new ProductFormDTO();
        productFormDTO.setProdName("테스트상품");
        productFormDTO.setProdSellStatus(ProdSellStatus.SELL);
        productFormDTO.setProdDetail("테스트 상품입니다.");
        productFormDTO.setPrice(1000);
        productFormDTO.setStockNumber(100);

        List<MultipartFile> multipartFileList = createMultipartFileList();
        long productId = productService.saveProduct(productFormDTO, multipartFileList);

        List<ProductImg> productImgList =
                productImgRepository.findByProductIdOrderByIdAsc(productId);
        Product product =productRepository.findById(productId)
                .orElseThrow(EntityNotFoundException::new);

        assertEquals(productFormDTO.getProdName(),  product.getProdName());
        assertEquals(productFormDTO.getProdSellStatus(), product.getProdSellStatus());
        assertEquals(productFormDTO.getProdDetail(), product.getProdDetail());
        assertEquals(productFormDTO.getPrice(), product.getPrice());
        assertEquals(productFormDTO.getStockNumber(), product.getStockNumber());
        assertEquals(multipartFileList.get(0).getOriginalFilename(), productImgList.get(0).getOriImgName());


    }


}