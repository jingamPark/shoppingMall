package com.example.toyproject_shoppingmall.dto;


import com.example.toyproject_shoppingmall.entity.Category;


import com.example.toyproject_shoppingmall.entity.Product;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import lombok.*;
import org.modelmapper.ModelMapper;


@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CategoryFormDTO {

    private static ModelMapper modelMapper =new ModelMapper();

    private Long id;
    private String title;
    private String description;
    private UserFormDTO userFormDTO;



    public Category createCategory() {
        return modelMapper.map(this, Category.class);
    }


}
