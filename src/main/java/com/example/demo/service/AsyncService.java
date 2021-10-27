package com.example.demo.service;

import com.example.demo.dto.BookSearchDto;
import com.example.demo.dto.ValidTestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@Slf4j
@Service
public class AsyncService {

    /**
     * Provider에게 유저 정보를 반환하고, 세션에 유저 정보를 저장
     */
    @Async
    public CompletableFuture<List<ValidTestDto.Req>> findUser() throws InterruptedException {
        var list = new ArrayList<ValidTestDto.Req>();
        list.add(ValidTestDto.Req.builder().query("query1").email("email1").build());
        list.add(ValidTestDto.Req.builder().query("query2").email("email2").build());
        list.add(ValidTestDto.Req.builder().query("query3").email("email3").build());
        Thread.sleep(1000L);
        return CompletableFuture.completedFuture(list);
    }

    /**
     * Provider에게 유저 정보를 반환하고, 세션에 유저 정보를 저장
     */
    @Async
    public CompletableFuture<List<BookSearchDto.Req>> findUser2(){
        var list = new ArrayList<BookSearchDto.Req>();
        list.add(BookSearchDto.Req.builder().query("query1").barcode("barcode1").build());
        list.add(BookSearchDto.Req.builder().query("query2").barcode("barcode2").build());
        list.add(BookSearchDto.Req.builder().query("query3").barcode("barcode3").build());
        return CompletableFuture.completedFuture(list);
    }

    /**
     * Provider에게 유저 정보를 반환하고, 세션에 유저 정보를 저장
     */
    @Async
    public CompletableFuture<List<BookSearchDto.Res.Document>> findUser3() throws InterruptedException  {
        var list = new ArrayList<BookSearchDto.Res.Document>();
        list.add(BookSearchDto.Res.Document.builder().category("category1").barcode("barcode1").build());
        list.add(BookSearchDto.Res.Document.builder().category("category2").barcode("barcode2").build());
        list.add(BookSearchDto.Res.Document.builder().category("category3").barcode("barcode3").build());
        Thread.sleep(1500L);
        return CompletableFuture.completedFuture(list);
    }
}
