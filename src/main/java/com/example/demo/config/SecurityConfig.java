package com.example.demo.config;

import com.example.demo.filter.SmsCertFilter;
import com.example.demo.handler.Oauth2SuccessHandler;
import com.example.demo.handler.SmsCertSuccessHandler;
import com.example.demo.service.CustomOAuth2UserService;
import com.example.demo.service.provider.SmsCertProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final Oauth2SuccessHandler oauth2SuccessHandler;

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
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

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
                        request.antMatchers(
                                "/**",
                                "/", "/login/**", "/3", "/sms-login", "/sms-login-process").permitAll()
                                .antMatchers("/swagger-ui.html").denyAll()
                                .anyRequest().authenticated()
                )
//                .exceptionHandling()
//                    .`
                .logout()
                    .deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout/**"))
                .and().formLogin()
                    .loginPage("/login")
                    .successForwardUrl("/")
                    .failureForwardUrl("/")
//                .and().addFilterAt(filter, UsernamePasswordAuthenticationFilter.class)
                .and().sessionManagement()
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(false)
                    .expiredUrl("/login")
                .and().and().oauth2Login()
                    .defaultSuccessUrl("/2")
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                    .and().successHandler(oauth2SuccessHandler)
//                .and().addFilterAfter(smsCertAuthenticationFilter(), CsrfFilter.class)
//                .and().rememberMe()
//                    .key("uniqueAndSecret")
//                    .alwaysRemember(true)
//                    .tokenValiditySeconds(60)
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

    private final SmsCertProvider smsCertProvider;
    private final SmsCertSuccessHandler smsCertSuccessHandler;

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(smsCertProvider);
    }

    @Bean
    public SmsCertFilter smsCertAuthenticationFilter() throws Exception {
        SmsCertFilter filter = new SmsCertFilter("/sms-login-process");
        filter.setAuthenticationManager(authenticationManagerBean());
        filter.setAuthenticationSuccessHandler(smsCertSuccessHandler);
        return filter;
    }
}
