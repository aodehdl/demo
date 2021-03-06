package com.example.demo.controller;

import com.example.demo.config.MapUtil;
import com.example.demo.dto.BookSearchDto;
import com.example.demo.dto.Response;
import com.example.demo.dto.ValidTestDto;
import com.example.demo.feign.KakaoFeignService;
import com.example.demo.service.AsyncService;
import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/main")
public class IndexController {
    private final KakaoFeignService kakaoFeignService;
    private final AsyncService asyncService;
    private final Gson gson;


    @GetMapping("")
    public ModelAndView main() throws InstantiationException, IllegalAccessException {
        ModelAndView modelAndView = new ModelAndView();

        var result = MapUtil.getData(gson.fromJson(json, Map.class), ArrayList.class, "addresses", 0, "addressElements", 1);
        log.info("{}",result);

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

    final String json = "{\n" +
            "    \"status\": \"OK\",\n" +
            "    \"meta\": {\n" +
            "        \"totalCount\": 1,\n" +
            "        \"page\": 1,\n" +
            "        \"count\": 1\n" +
            "    },\n" +
            "    \"addresses\": [\n" +
            "        {\n" +
            "            \"roadAddress\": \"경기도 성남시 분당구 불정로 6 그린팩토리\",\n" +
            "            \"jibunAddress\": \"경기도 성남시 분당구 정자동 178-1 그린팩토리\",\n" +
            "            \"englishAddress\": \"6, Buljeong-ro, Bundang-gu, Seongnam-si, Gyeonggi-do, Republic of Korea\",\n" +
            "            \"addressElements\": [\n" +
            "                {\n" +
            "                    \"types\": [\n" +
            "                        \"POSTAL_CODE\"\n" +
            "                    ],\n" +
            "                    \"longName\": \"13561\",\n" +
            "                    \"shortName\": \"\",\n" +
            "                    \"code\": \"\"\n" +
            "                }\n" +
            "            ],\n" +
            "            \"x\": \"127.10522081658463\",\n" +
            "            \"y\": \"37.35951219616309\",\n" +
            "            \"distance\": 20.925857741585514\n" +
            "        }\n" +
            "    ],\n" +
            "    \"errorMessage\": \"\"\n" +
            "}";
}
