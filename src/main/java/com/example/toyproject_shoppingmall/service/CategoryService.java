package com.example.toyproject_shoppingmall.service;

import com.example.toyproject_shoppingmall.dto.CategoryFormDTO;
import com.example.toyproject_shoppingmall.entity.Category;
import com.example.toyproject_shoppingmall.entity.Product;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.repository.CategoryRepository;
import com.example.toyproject_shoppingmall.repository.ProductRepository;
import com.example.toyproject_shoppingmall.repository.ShopUserRepository;
import groovy.util.logging.Log4j2;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@ToString
@Transactional
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ShopUserRepository shopUserRepository;
    private final ModelMapper modelMapper;

    // 저장 ,삭제, 참조하여 찾기, 모두찾기


    public Category saveCategory (CategoryFormDTO categoryFormDTO,ShopUser shopUser){

        Category category = Category.updateCategory(categoryFormDTO,shopUser);

        return  categoryRepository.save(category);
    }


    public Optional<Category> selectCategory(Long id) {
        return categoryRepository.findById(id);
    }

    public List<CategoryFormDTO> getAllCategories() {

        List<Category> categoryList =  categoryRepository.findAll();
        List<CategoryFormDTO> categoryFormDTOS
                = categoryList.stream().map(category -> modelMapper.map(category, CategoryFormDTO.class))
                .collect(Collectors.toUnmodifiableList());

        return categoryFormDTOS;
    }

    public void delCategory(Long id) {
        categoryRepository.deleteById(id);
    }



}
