package com.aivle06.bookservice.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name= "user_id")
    private Long id;

    @Column(nullable = false)
    private String pw;

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @CreationTimestamp
    private LocalDateTime member_since;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Book> books = new ArrayList<>();
    // getBooks()하다가 null 생길까봐 일단 빈 List생성


}
