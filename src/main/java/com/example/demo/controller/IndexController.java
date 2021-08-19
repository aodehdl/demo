package com.example.demo.controller;

import com.example.demo.dto.BookSearchDto;
import com.example.demo.dto.Response;
import com.example.demo.dto.ValidTestDto;
import com.example.demo.feign.KakaoFeignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Slf4j
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
    public Response<BookSearchDto.Res> temp(@AuthenticationPrincipal User user){
        log.debug("{}",user);
        BookSearchDto.Res res = kakaoFeignService.getBook("스프링");
        return Response.ok(res);
    }

    @PostMapping("/3")
    public String temp2(@Valid @RequestBody ValidTestDto.Req req){
        log.debug("{}", req);
        BookSearchDto.Res res = kakaoFeignService.getBook("스프링");
        return res.toString();
    }

    @PostMapping("/4")
    public String temp4(@RequestParam(name = "code") String code, @RequestParam(name = "state") String state){
        log.debug("{}, {}", code, state);
        return "";
    }

    @GetMapping("/sms-login")
    public ModelAndView smsLogin(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("smslogin");

        return modelAndView;
    }
}
