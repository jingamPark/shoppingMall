package com.example.toyproject_shoppingmall.dto;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserFormDTO {

    private Long id;

    @NotBlank(message = "아이디는 필수 입력입니다.")
    @Size(min = 3, max = 10, message = "아이디는 3글자 이상 10글자 이하 입니다.")
    private String loginId; //아이디

    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 8, max = 16, message = "비빌번호는 8글자 이상 16글자 이하 입니다.")
    private String password;   //비밀번호

    @NotBlank(message = "이름은 필수 입력 입니다..")
    @Size(min = 2, max = 8, message = "이름은 2글자 이상 8글자 이하 입니다.")
    private String name;       //유저의 이름

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "이메일 형식입니다. ex) 'email@naver.com' ")
    private String email;      //이메일

    @NotBlank(message = "전화번호를 입력해주세요")
    @Size(min = 11, max = 11, message = "전화번호는 ' - ' 없이 11글자 입니다.")
    private String tel;        //전화번호

    @NotBlank(message = "주소를 입력해주세요")
    @Size(min = 2, max = 8, message = "이름은 2글자 이상 8글자 이하 입니다.")
    private String address;    //주소




}
