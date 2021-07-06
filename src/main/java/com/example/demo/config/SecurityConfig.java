package com.example.demo.config;

import com.example.demo.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser(
//                        User.withDefaultPasswordEncoder()
//                                .username("user1")
//                                .password("1111")
//                                .roles("USER")
//                ).withUser(
//                User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password("2222")
//                        .roles("ADMIN")
//        );
//    }

    @Bean
    RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return roleHierarchy;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests(request->
                        request.antMatchers("/", "/login/**", "/3").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin()
                    .loginPage("/login")
                    .successForwardUrl("/")
                    .failureForwardUrl("/")
                .and().sessionManagement()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true)
                .and().and().oauth2Login()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
        ;
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(
                        PathRequest.toStaticResources().atCommonLocations()
                )
        ;
    }
}
