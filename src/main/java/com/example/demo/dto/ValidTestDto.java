package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class ValidTestDto {
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class Req {
        @Size(max = 10)
        @NotBlank
        private String query; //query   검색어   	String 필수여부 O

        @Email
        @NotBlank
        private String email;
    }
}
