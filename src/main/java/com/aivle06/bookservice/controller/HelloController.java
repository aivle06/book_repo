package com.aivle06.bookservice.controller;

import com.aivle06.bookservice.domain.Book;
import com.aivle06.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class HelloController {

    private final BookService bookService;

    // 새 책 등록 (POST /api/books)
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookService.createBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    // 전체 목록 조회 (GET /api/books/?page={page_num})
    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam("page")int page) {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // 특정 ID로 조회 (GET /api/books/{book_id})
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    // 업뎃 (PUT /api/books/{book_id})
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id,
                                           @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    // 삭제 (DELETE /api/books/{id})
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
