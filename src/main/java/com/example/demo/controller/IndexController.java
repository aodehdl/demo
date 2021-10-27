package com.example.demo.controller;

import com.example.demo.dto.BookSearchDto;
import com.example.demo.dto.Response;
import com.example.demo.dto.ValidTestDto;
import com.example.demo.feign.KakaoFeignService;
import com.example.demo.service.AsyncService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class IndexController {
    private final KakaoFeignService kakaoFeignService;
    private final AsyncService asyncService;

    @GetMapping("")
    public ModelAndView main(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping("/2")
    @Profile("dev")
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

    @GetMapping("/4")
    public String temp4()  throws InterruptedException {
        StopWatch stopWatch =new StopWatch();
        stopWatch.start();
        var price1 = asyncService.findUser();
        var price2 = asyncService.findUser2();
        var price3 = asyncService.findUser3();
        List<CompletableFuture> futures = Arrays.asList(price1,
                price2,
                price3);
        //다른 로직
        CompletableFuture.allOf(price1,price2,price3)
                .thenAccept(s -> {
                    stopWatch.stop();
                    log.info("밀리 세컨드 : {}", stopWatch.getTotalTimeMillis());
                    var result = futures.stream()
                            .map(pageContentFuture -> pageContentFuture.join())
                            .collect(Collectors.toList());
                    log.info(result.toString());
                });

        return "";
    }

    @GetMapping("/sms-login")
    public ModelAndView smsLogin(){
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("smslogin");

        return modelAndView;
    }

    final String str = "Map으로 리턴 하며 의미는 아래와같다<br/>" +
            "email 이메일<br/>" +
            "name 이름<br/>" +
            "email 이메일<br/>" +
            "email 이메일<br/>" +
            "email 이메일<br/>" +
            "email 이메일<br/>";
}
