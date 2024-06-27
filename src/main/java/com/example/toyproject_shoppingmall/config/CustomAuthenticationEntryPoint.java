package com.example.toyproject_shoppingmall.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    //http request header에
    //XMLHttpRequest라는 값이 세팅되어 요청이 옴
    //인증되지 않은 사용자가 ajax
    //Unauthorized 에러발생

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        if ("XMLHttpRequest".equals(request.getHeader("x-request-with"))) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } else {
            response.sendRedirect("/members/login");
        }
    }





}
