# 📚 Book Management API

Spring Boot 기반의 책 관리 REST API

---

## Entity

```java
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String author;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @Column(columnDefinition = "TEXT")
    private String image_url;
}
```

---

## Service

```java
@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Book createBook(BookRequestDTO.Create book) {
        Book bookMapperEntity = bookMapper.toEntity(book);
        return bookRepository.save(bookMapperEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("책이 존재하지 않습니다."));
    }

    @Override
    public BookListResponseDTO getBookListResponseDTOById(Long id) {
        Book bookById = getBookById(id);
        return bookMapper.toListResponseDTO(bookById);
    }

    @Override
    public List<BookListResponseDTO> getAllBookListResponseWithPaging(int page, int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Book> result = bookRepository.findAll(pageable);
        return result.stream()
            .map(bookMapper::toListResponseDTO)
            .toList();
    }

    @Override
    public BookDetailResponseDTO getBookDetailResponseDTOById(Long id) {
        Book bookById = getBookById(id);
        return bookMapper.toDetailResponseDTO(bookById);
    }

    @Override
    public Book updateBook(Long id, BookRequestDTO.Update updateBook) {
        Book b = getBookById(id);
        bookMapper.updateFromDTO(updateBook, b);
        return bookRepository.save(b);
    }

    @Override
    public void deleteBook(Long id) {
        Book b = getBookById(id);
        bookRepository.delete(b);
    }
}
```

---

## Controller

```java
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody BookRequestDTO.Create book) {
        Book createdBook = bookService.createBook(book);
        return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<DataResponse<BookListResponseDTO>> getAllBooks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        DataResponse<BookListResponseDTO> response = new DataResponse<>(
            bookService.getAllBookListResponseWithPaging(page, size)
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDetailResponseDTO> getBookById(@PathVariable Long id) {
        BookDetailResponseDTO dto = bookService.getBookDetailResponseDTOById(id);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Long id,
            @RequestBody BookRequestDTO.Update book
    ) {
        Book updatedBook = bookService.updateBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}
```

---

## API 명세

### 1. 책 등록
```
POST /api/books
```

**요청:**
```json
{
    "title": "string",
    "content": "string",
    "author": "string",
    "image_url": "string"
}
```

**응답 (201):**
```json
{
    "id": 1,
    "title": "string",
    "content": "string",
    "author": "string",
    "created_at": "2025-12-08T10:16:00",
    "updated_at": "2025-12-08T10:16:00",
    "image_url": "string"
}
```

---

### 2. 전체 책 조회 (페이징)
```
GET /api/books?page=0&size=10
```

**파라미터:**
- `page` (int, default: 0) - 페이지 번호
- `size` (int, default: 10) - 페이지당 개수

**응답 (200):**
```json
{
    "data": [
        {
            "id": 1,
            "title": "string",
            "author": "string",
            "created_at": "2025-12-08T10:16:00"
        }
    ]
}
```

---

### 3. 책 상세 조회
```
GET /api/books/{id}
```

**경로 변수:**
- `id` (long) - 책 ID

**응답 (200):**
```json
{
    "id": 1,
    "title": "string",
    "content": "string",
    "author": "string",
    "created_at": "2025-12-08T10:16:00",
    "updated_at": "2025-12-08T10:16:00",
    "image_url": "string"
}
```

**에러 (404):**
```json
{
    "message": "책이 존재하지 않습니다."
}
```

---

### 4. 책 수정
```
PUT /api/books/{id}
```

**경로 변수:**
- `id` (long) - 책 ID

**요청:**
```json
{
    "title": "string",
    "content": "string",
    "author": "string"
}
```

**응답 (200):**
```json
{
    "id": 1,
    "title": "string",
    "content": "string",
    "author": "string",
    "created_at": "2025-12-08T10:16:00",
    "updated_at": "2025-12-08T10:20:00",
    "image_url": "string"
}
```

---

### 5. 책 삭제
```
DELETE /api/books/{id}
```

**경로 변수:**
- `id` (long) - 책 ID

**응답 (204):**
```
No Content
```

---

## 기술 스택

- Java 17+
- Spring Boot 3.x
- Spring Data JPA
- MySQL
- Lombok
- MapStruct