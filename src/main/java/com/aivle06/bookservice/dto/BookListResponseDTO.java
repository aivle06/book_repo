package com.aivle06.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookListResponseDTO {

    Long id;
    String title;
    String author;
    LocalDateTime created_at;

    
}
