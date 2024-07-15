package com.example.toyproject_shoppingmall.dto;

import com.example.toyproject_shoppingmall.constant.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter
@ToString
public class UserSearchDTO {

    private Role role;

    private String searchDateType;

    private String searchBy;

    private String searchQuery = "";

}
