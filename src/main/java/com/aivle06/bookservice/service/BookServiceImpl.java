package com.aivle06.bookservice.service;

import com.aivle06.bookservice.domain.Book;
import com.aivle06.bookservice.dto.BookDetailResponseDTO;
import com.aivle06.bookservice.dto.BookListResponseDTO;
import com.aivle06.bookservice.dto.BookRequestDTO;
import com.aivle06.bookservice.exception.ResourceNotFoundException;
import com.aivle06.bookservice.mapper.BookMapper;
import com.aivle06.bookservice.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    //저장
    @Override
    public Book createBook(BookRequestDTO.Create book){
        Book bookMapperEntity = bookMapper.toEntity(book);
        return bookRepository.save(bookMapperEntity);
    }

    //전체 조회
    @Override
    @Transactional(readOnly=true)
    public List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    //단건 조회
    @Override
    @Transactional(readOnly = true)
    public Book getBookById(Long id){
        return bookRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("책이 존재하지 않습니다."));
    }

    // 리스트 Response용
    @Override
    public BookListResponseDTO getBookListResponseDTOById(Long id){
        Book bookById = getBookById(id);
        return bookMapper.toListResponseDTO(bookById);
    }

    // 페이 적용 리스트 Response
    public Page<BookListResponseDTO> getAllBookListResponseWithPaging(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Book> result = bookRepository.findAll(pageable);

        return result.map(bookMapper::toListResponseDTO);
    }


    // 책 상세정보 Response용
    public BookDetailResponseDTO getBookDetailResponseDTOById(Long id){
        Book bookById = getBookById(id);
        return bookMapper.toDetailResponseDTO(bookById);
    }

    //업데이트
    @Override
    public Book updateBook(Long id, BookRequestDTO.Update updateBook){
        Book b = getBookById(id);
        bookMapper.updateFromDTO(updateBook, b);
        return bookRepository.save(b);
    }

    //삭제
    public void deleteBook(Long id){
        Book b = getBookById(id);
        bookRepository.delete(b);
    }


}
