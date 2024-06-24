package com.example.toyproject_shoppingmall.controller;


import com.example.toyproject_shoppingmall.dto.ProductFormDTO;
import com.example.toyproject_shoppingmall.dto.ProductImgDTO;
import com.example.toyproject_shoppingmall.dto.ProductSearchDTO;
import com.example.toyproject_shoppingmall.entity.Product;
import com.example.toyproject_shoppingmall.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;


import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/products")
public class AdminProductController {

    private final ProductService productService;

    @GetMapping(value = "/new")
    public String getProductForm(Principal principal,Model model) {

        model.addAttribute("productFormDTO",new ProductFormDTO());

        return "products/productForm";
    }

    @PostMapping("/new")
    public String postProductForm(@Valid ProductFormDTO productFormDTO, BindingResult bindingResult,
                              Model model, @RequestParam("productImgFile")List<MultipartFile> productImgFileList) throws IOException {

        if (bindingResult.hasErrors()) {
            log.info("에러  ");
            model.addAttribute("errorMessage", "에러가 발생???");
            return "products/productForm";
        }
        if (productImgFileList.get(0).isEmpty() && productFormDTO.getId() == null ) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입니다.");
            return "products/productForm";
        }

        try {
            productService.saveProduct(productFormDTO, productImgFileList);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "상품등록 중 에러가 발생 하였습니다");
            return "products/productForm";
        }



        return "redirect:/";
    }


    @GetMapping(value = "/{productId}")
    public String productDtl(@PathVariable("productId") long productId, Model model) {

        log.info("제품 아이디는 가져옴?? >>>>"+productId);

        try {
            ProductFormDTO productFormDTO = productService.getProductDtl(productId);
            log.info("dto는 ??? : >>>"+productFormDTO);
            model.addAttribute("productFormDTO",productFormDTO);
        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 상품 입니다.");
            model.addAttribute("productFormDTO", new ProductFormDTO());
            return "products/productForm";

        }

        return "products/productForm";
    }

    @PostMapping(value = "/{productId}")
    public String productUpdate(@Valid ProductFormDTO productFormDTO, BindingResult bindingResult,
                                @RequestParam("productImgFile") List<MultipartFile> productImgFileList,Model model){

        log.info("이미지??"+productImgFileList);

        if (bindingResult.hasErrors()) {

            return "products/productForm";
        }
        if (productImgFileList.get(0).isEmpty() && productFormDTO.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 상품 이미지는 필수 입력 값 입니다.");
            return "products/productForm";
        }

        try {
            productService.updateProduct(productFormDTO, productImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage","상품 수정 중 오류가 발생 하였습니다.");
            return "products/productForm";

        }
        return "redirect:/";
    }


    @GetMapping({"/prodMng", "/prodMng/{page}"})
    public String productManage(ProductSearchDTO productSearchDTO, @PathVariable("page") Optional<Integer> page
            , Model model) {


        Pageable pageable =
                PageRequest.of(page.isPresent() ? page.get() : 0,3);

        Page<Product> products
                = productService.getAdminProductPage(productSearchDTO, pageable );


        model.addAttribute("products",products);
        //log.info("검색어 가져오나요?? : "+productSearchDTO);
        model.addAttribute("productSearchDTO",productSearchDTO);

        model.addAttribute("maxPage", 5);

        return "/products/list";
    }




}//controller class
