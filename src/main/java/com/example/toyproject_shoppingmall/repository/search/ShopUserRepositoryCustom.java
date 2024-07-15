package com.example.toyproject_shoppingmall.repository.search;

import com.example.toyproject_shoppingmall.dto.UserSearchDTO;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ShopUserRepositoryCustom  {

    Page<ShopUser> getAdminShopUserPage(UserSearchDTO userSearchDTO, Pageable pageable);

}
