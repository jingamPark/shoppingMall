package com.example.toyproject_shoppingmall.controller;


import com.example.toyproject_shoppingmall.dto.UserFormDTO;
import com.example.toyproject_shoppingmall.dto.UserPasswordDTO;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.service.ShopUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final ShopUserService shopUserService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;




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
        if (!userFormDTO.getPassword().equals(userFormDTO.getPassword2())){
            bindingResult.rejectValue("password2","passwordInCorrect"
                    ,"2개의 패스워드가 일치하지 않습니다.");
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



    @GetMapping("/idcheck")
    public String getIdCheck() {


        return "/users/idCheck";
    }

    @PostMapping("/idcheck")
    public String postIdCheck(String email, String name ,Model model) {


        log.info("이메일 : " + email);
        log.info("이름 : " + name);

        ShopUser findId = shopUserService.findLoginId(email, name);
        log.info(findId);
        if (findId == null) {
            model.addAttribute("loginId","없음");
            return "/users/idCheck";
        }
        model.addAttribute("loginId",findId.getLoginId());


        return "/users/idCheck";
    }



    @GetMapping("/passcheck")
    public String getPassCheck() {


        return "/users/passcheck";
    }

    @PostMapping("/passcheck")
    public String passCheck(String loginId, String email, Model model) {
        log.info(loginId);
        log.info(email);
        
        ShopUser findPass = shopUserService.findPassword(loginId,email);
        if (findPass != null){
            log.info("성공");
            model.addAttribute("email", findPass.getEmail());
            return "redirect:/users/passchange";
        }else {
            log.info("실패 다시 입력");
            model.addAttribute("err", "아이디 또는 이메일을 확인해주세요");
            return "/users/passcheck";
        }


    }


    @GetMapping("/passchange")
    public String passChange(Model model, UserPasswordDTO userPasswordDTO){

        model.addAttribute("userPasswordDTO", userPasswordDTO);


        return "/users/passChange";
    }

    @PostMapping("/passchange")
    public String passChange(UserPasswordDTO userPasswordDTO,String loginId, String email, Model model) {

        log.info("변경 한 비밀번호 : " + userPasswordDTO.getNewPassword());
        log.info("변경 한 비밀번호2 : " + userPasswordDTO.getNewPassword2());



//        if (!userFormDTO.getPassword().equals(userFormDTO.getPassword2())){
//            bindingResult.rejectValue("password2","passwordInCorrect"
//                    ,"2개의 패스워드가 일치하지 않습니다.");
//            return "users/userForm";
//        }



        return "/users/login";
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
