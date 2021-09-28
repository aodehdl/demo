package com.example.demo.controller;

import com.example.demo.dto.BookSearchDto;
import com.example.demo.dto.Response;
import com.example.demo.dto.ValidTestDto;
import com.example.demo.feign.KakaoFeignService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/home")
public class IndexController2 {
    private final KakaoFeignService kakaoFeignService;

    @GetMapping("")
    public ModelAndView main(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping("/2")
    public Response<BookSearchDto.Res> temp(@AuthenticationPrincipal User user
            , @RequestBody(required = false) BookSearchDto.Req req){
        log.debug("{}",user);
        BookSearchDto.Res res = kakaoFeignService.getBook("스프링");
        return Response.ok(res);
    }

    @PostMapping("/3")
    @ApiOperation("@RequestBody String 파라메터 테스트")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "query, email 만씀",
                    required = true, dataType = "BookSearchDto.Req", paramType = "body")})
    @ApiResponses( @ApiResponse(code = 200, message = str))
    public String temp2(@Valid @RequestBody @ApiIgnore ValidTestDto.Req req){
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

    final String str = "Map으로 리턴 하며 의미는 아래와같다" +
            "email 이메일" +
            "name 이름" +
            "email 이메일" +
            "email 이메일" +
            "email 이메일" +
            "email 이메일";
}
