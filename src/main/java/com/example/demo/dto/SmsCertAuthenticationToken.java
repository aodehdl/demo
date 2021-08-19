package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SmsCertAuthenticationToken implements Authentication {
    // 추후 dto 객체로 쓸수 있음
    private String principal;
    private String credentials;
    private Set<GrantedAuthority> authorities;
    private String details;
    private boolean authenticated;

    public SmsCertAuthenticationToken(String principal, String credentials) {
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    @Override
    public String getName() {
        return this.principal;
    }

}
