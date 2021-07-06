package com.example.demo.controller;

import com.example.demo.dto.BookSearchDto;
import com.example.demo.feign.KakaoFeignService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequiredArgsConstructor
public class IndexController {
    private final KakaoFeignService kakaoFeignService;

    @GetMapping("/")
    public ModelAndView main(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping("/2")
    public String temp(){
        BookSearchDto.Res res = kakaoFeignService.getBook("스프링");
        return res.toString();
    }
}
