package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
@Profile({"dev", "local"})
public class SwaggerConfig {
    // 기본 swagger 선언
    @Bean
    public Docket api() {
        return setBeanDocket("def", "com.example.demo", "/**");
    }

    @Bean
    public Docket apiV1() {
        return setBeanDocket("controller1", "com.example.demo.controller", "/main/**");

    }

    @Bean
    public Docket apiV2() {
        return setBeanDocket("controller2", "com.example.demo.controller", "/home/**");
    }

    private Docket setBeanDocket(String spec, String basePackage, String path){
        return new Docket(DocumentationType.SWAGGER_2)
                .consumes(Collections.singleton(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .produces(Collections.singleton(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .groupName(spec)
                .select()
                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .paths(PathSelectors.ant(path))
                .build();
    }
}
