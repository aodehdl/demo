package com.example.demo.feign;

import com.example.demo.config.KakaoApiFeignConfig;
import com.example.demo.dto.BookSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Service
@FeignClient( name = "kakaoBookSearch", url="${kakao.url}", configuration = KakaoApiFeignConfig.class )
public interface KakaoFeignService {
    /**
     * 카카오 책 검색을 이용하여 목록 가져오기
     */
    @GetMapping("/v3/search/book")
    BookSearchDto.Res getBook(
            @RequestParam("query") String query,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "target", required = false) String target,
            @RequestParam(value = "page", required = false) String page,
            @RequestParam(value = "size", required = false) int size);

    @GetMapping("/v3/search/book")
    BookSearchDto.Res getBook(
            @RequestParam("query") String query);
}
