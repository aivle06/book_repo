package com.aivle06.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookRequestDTO {

    // 책 등록
    public static class Create{
        String title;
        String content;
        String author;

    }

    // 책 수정
    public static class Update{
        String title;
        String content;
        String author;
    }



}
