package com.aivle06.bookservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookDetailResponseDTO {

    String title;

    String content;

    String author;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    LocalDateTime created_at;

    String image_url;

}
