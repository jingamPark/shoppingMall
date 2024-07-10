package com.example.toyproject_shoppingmall.service;

import com.example.toyproject_shoppingmall.dto.CategoryFormDTO;
import com.example.toyproject_shoppingmall.dto.UserFormDTO;
import com.example.toyproject_shoppingmall.entity.Category;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.repository.CategoryRepository;
import com.example.toyproject_shoppingmall.repository.ShopUserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
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


    public Category saveCategory(CategoryFormDTO categoryFormDTO, String loginId) {

        ShopUser findShopuser = shopUserRepository.findByLoginId(loginId);
        if (findShopuser == null) {
            throw new IllegalArgumentException("유저가 없습니다.");
        }

        ShopUser shopUser = findShopuser;

        Category category = new Category();
        category.updateCategory(categoryFormDTO, shopUser);

        return categoryRepository.save(category);
    }

    public Long updateCategory(CategoryFormDTO categoryFormDTO,String loginId) {

        ShopUser shopUser =shopUserRepository.findByLoginId(loginId);

        Category category = categoryRepository.findById(categoryFormDTO.getId())
                .orElseThrow(EntityNotFoundException::new);



        category.updateCategory(categoryFormDTO,shopUser);

        return category.getId();
    }

    public CategoryFormDTO selectCategory(Long categoryId) {


        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(EntityNotFoundException::new);

        CategoryFormDTO categoryFormDTO = modelMapper.map(category, CategoryFormDTO.class);
//        categoryFormDTO.setUserFormDTO(modelMapper.map(category.getShopUser(), UserFormDTO.class));


        return categoryFormDTO;

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
