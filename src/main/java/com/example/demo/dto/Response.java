package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Response<T> {
    private int stateCode = 200;
    private String message = "성공하셨습니다.";
    private T result;

    public static <T> Response<T> ok(T data) {
        return (Response<T>) Response.builder()
                .result(data)
                .build();
    }
}
