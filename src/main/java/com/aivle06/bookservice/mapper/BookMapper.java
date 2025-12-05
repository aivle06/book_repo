package com.aivle06.bookservice.mapper;

import com.aivle06.bookservice.domain.Book;
import com.aivle06.bookservice.dto.BookRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(BookRequestDTO.Create dto);

    Book toEntity(BookRequestDTO.Update dto);
}
