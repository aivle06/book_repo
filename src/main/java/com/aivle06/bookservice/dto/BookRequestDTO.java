package com.aivle06.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


public class BookRequestDTO {

    // 책 등록
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Create{
        String title;
        String content;
        String author;

    }

    // 책 수정
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Update{
        String title;
        String content;
        String author;
        String image_url;
    }



}
