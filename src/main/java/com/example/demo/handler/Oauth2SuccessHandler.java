package com.example.demo.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Slf4j
@Configuration
public class Oauth2SuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {

        // 회원가입이 필요한 경우
        // 비로그인 처리 할거
        if(true){
            authentication = new AnonymousAuthenticationToken("guest-key", "guest",
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_GUEST")));
            SecurityContextHolder.getContext().setAuthentication(null);
            response.sendRedirect("/sms-login");
            authentication = null;
        }

        super.onAuthenticationSuccess(request, response, authentication);
    }
}
