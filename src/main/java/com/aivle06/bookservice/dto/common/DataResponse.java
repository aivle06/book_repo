package com.aivle06.bookservice.dto.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataResponse<T> {
    private List<T> data;

    private int totalPages;     // 총 페이지 수
    private long totalElements; // 전체 데이터 개수
    private int page;           // 현재 페이지 번호
    private int size;           // 요청한 페이지 크기
}
