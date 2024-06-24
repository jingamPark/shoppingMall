package com.example.toyproject_shoppingmall.controller;


import com.example.toyproject_shoppingmall.dto.UserFormDTO;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.service.ShopUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final ShopUserService shopUserService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping(value = "/signup")
    public String getSignUp(Model model) {

        model.addAttribute("userFormDTO",new UserFormDTO());

        return "users/userForm";
    }

    @PostMapping(value = "/signup")
    public String postSignUp(@Valid UserFormDTO userFormDTO, BindingResult bindingResult
    , Model model) {

        if (bindingResult.hasErrors()) {
            return "users/userForm";
        }
        try {
            ShopUser shopUser = ShopUser.createUser(userFormDTO, passwordEncoder);
            shopUserService.saveUser(shopUser);

        }catch (IllegalStateException e){
            model.addAttribute("errorMsg", e.getMessage());
            return "users/userForm";
        }

//        ShopUser shopUser = ShopUser.createUser(userFormDTO, passwordEncoder);
//        shopUserService.saveUser(shopUser);
        return "redirect:/";
    }


    @GetMapping(value ="/login")
    public String getLogin() {



        return "users/loginForm";
    }

    @GetMapping(value ="/login/error")
    public String postLogin(Model model) {
        model.addAttribute("loginErrorMsg", "아이디 또는 비밀번호를 확인해주세요");

        return "users/loginForm";
    }



    @GetMapping("/idchek")
    public String getIdchek() {


        return "/users/idchek";
    }

    @PostMapping("/idchek")
    public String postIdchek(String email, String name ,Model model) {


        log.info("이메일 : " + email);
        log.info("이름 : " + name);

        ShopUser findId = shopUserService.findLoginId(email, name);
        log.info(findId);
        if (findId == null) {
            model.addAttribute("loginId","없음");
            return "/users/idchek";
        }
        model.addAttribute("loginId",findId.getLoginId());


        return "/users/idchek";
    }



    @GetMapping("/passcheck")
    public String getPasscheck() {


        return "/users/passcheck";
    }

    @PostMapping("/passcheck")
    public String postPasscheck(String loginId, String email, Model model) {


        ShopUser findPass = shopUserService.findPassword(loginId,email);
        if (findPass ==null) {
            model.addAttribute("password", "검색결과 없음 재검색 바람");
            return "/users/passcheck";
        }
        model.addAttribute("password",findPass.getPassword());
        return "/users/passcheck";
    }




    @GetMapping("/modify")
    public String modify() {


        return "/users/modify";
    }

    @GetMapping("/profile/{shopuserId}")
    public String profile(Model model) {



        return "/users/profile";
    }


}
