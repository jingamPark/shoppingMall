package com.example.toyproject_shoppingmall.controller;

import com.example.toyproject_shoppingmall.dto.UserSearchDTO;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.service.ShopUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Log4j2
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/users")
public class AdminUserController {

    private final ShopUserService shopUserService;


    @GetMapping(value = {"/list","/list/{page}"})
    public String userList(UserSearchDTO userSearchDTO, @PathVariable("page")Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);

        Page<ShopUser> users = shopUserService.getAdminUserListPage(userSearchDTO,pageable);
        model.addAttribute("users",users);
        model.addAttribute("userSearchDTO",userSearchDTO);
        model.addAttribute("maxPage", 5);

        return "/users/userList";
    }


}
