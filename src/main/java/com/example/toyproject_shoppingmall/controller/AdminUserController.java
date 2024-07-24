package com.example.toyproject_shoppingmall.controller;

import com.example.toyproject_shoppingmall.constant.Role;
import com.example.toyproject_shoppingmall.dto.PassChangeDTO;
import com.example.toyproject_shoppingmall.dto.UserFormDTO;
import com.example.toyproject_shoppingmall.dto.UserPasswordDTO;
import com.example.toyproject_shoppingmall.dto.UserSearchDTO;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.service.ShopUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
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

    @GetMapping(value = "/{shopUserId}")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    public String userManagement(@PathVariable("shopUserId")Long shopUserId, Model model
            , UserPasswordDTO userPasswordDTO, Principal principal
    ) {


        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            boolean isAdmin = authorities.stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

            if (!isAdmin) {
                // 관리자가 아닐 경우 접근 차단 로직
                return "/";
            }

            model.addAttribute("authorities", authorities);
            model.addAttribute("isAdmin", true);
        }

        UserFormDTO userFormDTO = shopUserService.adminUserManagement(shopUserId);
        log.info("받은 유저아이디 : " + shopUserId);
        model.addAttribute("userFormDTO", userFormDTO);
        model.addAttribute("userPasswordDTO",userPasswordDTO);

        return "/users/adminUserMange";

    }

    @PostMapping(value = "/modify/{shopUserId}")
    public String adminUserProfileModify(@PathVariable("shopUserId") Long shopUserId,
     UserFormDTO userFormDTO,BindingResult bindingResult,Model model) {

        if (bindingResult.hasErrors()) {
            return "/users/adminUserMange";
        }

        log.info("유저아이디 들어옴??/"+shopUserId);
        log.info(userFormDTO.getId());


        try {
            shopUserService.adminUserModify(userFormDTO,shopUserId);
        }catch (IllegalStateException e){
            model.addAttribute("errorMsg", e.getMessage());
            return "/users/adminUserMange";
        }



        return "/users/adminUserMange";
    }










}
