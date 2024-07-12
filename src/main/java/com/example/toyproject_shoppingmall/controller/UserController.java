package com.example.toyproject_shoppingmall.controller;


import com.example.toyproject_shoppingmall.dto.PassChangeDTO;
import com.example.toyproject_shoppingmall.dto.UserFormDTO;
import com.example.toyproject_shoppingmall.dto.UserPasswordDTO;
import com.example.toyproject_shoppingmall.entity.ShopUser;
import com.example.toyproject_shoppingmall.repository.ShopUserRepository;
import com.example.toyproject_shoppingmall.service.ShopUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final ShopUserService shopUserService;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final ShopUserRepository shopUserRepository;


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



    @GetMapping("/passfind")
    public String passFind() {


        return "/users/passFind";
    }

    @PostMapping("/passfind")
    public String passFind(String loginId, String email, Model model) {
        log.info(loginId);
        log.info(email);
        
        ShopUser findPass = shopUserService.findPassword(loginId,email);
        if (findPass != null){
            log.info("성공");
            model.addAttribute("loginId", findPass.getLoginId()); //비밀번호를 변경할 아이디
            //저장
            return "/users/passreset";
        }else {
            log.info("실패 다시 입력");
            model.addAttribute("err", "아이디 또는 이메일을 확인해주세요");
            return "/users/passFind";
        }


    }




    @PostMapping("/passreset")
    public String passReset(@Valid UserPasswordDTO userPasswordDTO, String loginId) {

        log.info("받아온 loginId:" + loginId);

        shopUserService.resetPassword(loginId, userPasswordDTO);


        return "users/loginForm";
    }


    @GetMapping("/profile")
    public String profile(Principal principal, Model model, UserFormDTO userFormDTO, PassChangeDTO passChangeDTO) {


        userFormDTO =  shopUserService.profile(principal.getName());

        model.addAttribute("userFormDTO", userFormDTO);
        model.addAttribute("passChangeDTO",passChangeDTO);

        return "/users/profile";
    }


    @PostMapping("/modify")
    public String modify(UserFormDTO userFormDTO,Principal principal
            ,BindingResult bindingResult,Model model) {
        String loginId = principal.getName();


            shopUserService.modify(userFormDTO,loginId);


        return "redirect:/users/profile";
    }

    @PostMapping("/passchange")
    public String passChange(PassChangeDTO passChangeDTO , Principal principal) {

        log.info(passChangeDTO.toString());


        shopUserService.changePassword(passChangeDTO, principal.getName());


        return "redirect:/users/logout" ;
    }




}
