package com.aivle06.bookservice.controller;

import com.aivle06.bookservice.domain.User;
import com.aivle06.bookservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //CRUD
    //Create POST
    @PostMapping
    public ResponseEntity<User> createBook(){
        User user = new User();
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    //Read GET

    //Update PUT

    //Delete DELETE
}
