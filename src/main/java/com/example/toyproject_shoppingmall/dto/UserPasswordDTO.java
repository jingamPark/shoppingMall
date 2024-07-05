package com.example.toyproject_shoppingmall.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordDTO {


    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 8, max = 16, message = "비빌번호는 8글자 이상 16글자 이하 입니다.")
    private String newPassword;   //변경하는 비밀번호


    @NotBlank(message = "비밀번호는 필수 입력입니다.")
    @Size(min = 8, max = 16, message = "비빌번호는 8글자 이상 16글자 이하 입니다.")
    private String newPassword2;   //변경하는 비밀번호

}
