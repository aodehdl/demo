package com.example.demo.controller;

import com.example.demo.dto.BookSearchDto;
import com.example.demo.dto.Response;
import com.example.demo.dto.ValidTestDto;
import com.example.demo.feign.KakaoFeignService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.locationtech.proj4j.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalField;
import java.util.concurrent.TimeUnit;

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
        final LocalTime init_time = LocalTime.of(0,0,0);
        LocalTime time = init_time;
        time.with(ChronoField.HOUR_OF_DAY, 15);
        LocalDate.now().with(ChronoField.ALIGNED_WEEK_OF_YEAR, 2);
        log.info("a: {}",  time.with(ChronoField.MINUTE_OF_DAY, 120).format(DateTimeFormatter.ofPattern("HHmmss")));
        LocalTime time2 = LocalTime.parse("012000", DateTimeFormatter.ofPattern("HHmmss"));
        log.info("b: {}", time2.get(ChronoField.MINUTE_OF_DAY));
        log.info("c: {}", init_time);
        log.info("d: {}", ChronoUnit.SECONDS.between(LocalDateTime.now(),
                LocalDate.now().atTime(LocalTime.parse("060000", DateTimeFormatter.ofPattern("HHmmss"))).plusDays(1)
                ));


        log.info("e: {}", ChronoUnit.SECONDS.between( LocalDateTime.from(LocalDate.parse("20211007", DateTimeFormatter.BASIC_ISO_DATE).atTime(init_time)),
                LocalDateTime.from(LocalDate.parse("20211009", DateTimeFormatter.BASIC_ISO_DATE).atTime(init_time))));
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

    @GetMapping("/4")
    public String temp4(){

        CRSFactory crsFactory = new CRSFactory();
        CoordinateReferenceSystem WGS84 = crsFactory.createFromParameters("WGS84",
                "+proj=longlat +ellps=WGS84 +datum=WGS84 +no_defs ");
        //ESPG:5179 좌표계 - 네이버 지도가 사용
        CoordinateReferenceSystem UTM = crsFactory.createFromParameters("UTM",
                "+proj=tmerc +lat_0=38 +lon_0=127.5 +k=0.9996 +x_0=1000000 +y_0=2000000 +ellps=GRS80 +units=m +no_defs");
        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CoordinateTransform utmToWgs = ctFactory.createTransform(UTM, WGS84);
        CoordinateTransform wgsToUtm = ctFactory.createTransform(WGS84, UTM);
        ProjCoordinate result_utm= new ProjCoordinate(), result = new ProjCoordinate();
        wgsToUtm.transform(new ProjCoordinate(127.020600, 37.559841 ), result_utm);
        utmToWgs.transform(result_utm, result);
        log.info("{}, {}", result_utm, result);
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
