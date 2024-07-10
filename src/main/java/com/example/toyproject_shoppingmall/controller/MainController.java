package com.example.toyproject_shoppingmall.controller;


import com.example.toyproject_shoppingmall.dto.MainProductDTO;
import com.example.toyproject_shoppingmall.dto.ProductFormDTO;
import com.example.toyproject_shoppingmall.dto.ProductSearchDTO;
import com.example.toyproject_shoppingmall.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.Optional;

@Controller

@RequiredArgsConstructor

public class MainController {
    private static final Logger log = LoggerFactory.getLogger(MainController.class);
    private final ProductService productService;


    @GetMapping(value = {"/","/{page}","/list/{title}"})
    public String main(ProductSearchDTO productSearchDTO
            , @PathVariable Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() :0 ,4);

        Page<MainProductDTO> products = productService
                .getMainProductPage(productSearchDTO, pageable);

        model.addAttribute("productSearchDTO", productSearchDTO);
        log.info("페이지 정보 ??? : "+products.toString());
        model.addAttribute("products", products);

        model.addAttribute("maxPage", 5);



        return "main";
    }


}

