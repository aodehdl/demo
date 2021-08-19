package com.example.demo.service.provider;

import com.example.demo.dto.SmsCertAuthenticationToken;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

@Configuration
public class SmsCertProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String certNum = (String) authentication.getCredentials();
        String phoneNum = (String) authentication.getPrincipal();

        // 일회용 비밀번호 인증
        if(!("12341234".equals(certNum) && "01099608281".equals(phoneNum))){
            throw new BadCredentialsException("인증 실패");
        }

        return new SmsCertAuthenticationToken(phoneNum, null,  Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
                                            null, true);
    }

    @Override
    public boolean supports(Class<?> authenticate) {
        return authenticate == SmsCertAuthenticationToken.class;
    }
}
