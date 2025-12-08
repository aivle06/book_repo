package com.aivle06.bookservice.service;

import com.aivle06.bookservice.domain.Book;
import com.aivle06.bookservice.dto.BookDetailResponseDTO;
import com.aivle06.bookservice.dto.BookListResponseDTO;
import com.aivle06.bookservice.dto.BookRequestDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    // 저장
    Book createBook(BookRequestDTO.Create book);
    //implements에서 충돌해서 Long에서 Book으로 다시 바꿨습니다

    // 단건 조회
    Book getBookById(Long id);

    // 다건 조회
    List<Book> getAllBooks();

    // 리스트 Response용
    BookListResponseDTO getBookListResponseDTOById(Long id);

    // 다건 리스트 Response용 + 페이징
    Page<BookListResponseDTO> getAllBookListResponseWithPaging(int page, int size);

    // 책 상세정보 Response용
    BookDetailResponseDTO getBookDetailResponseDTOById(Long id);

    // 업데이트
    Book updateBook(Long id, BookRequestDTO.Update updateBook);
    //저장하면서 Book이 return되면서 void랑 충돌했습니다;;

    // 삭제
    void deleteBook(Long id);

}
