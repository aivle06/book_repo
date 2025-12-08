package com.aivle06.bookservice.controller;

import com.aivle06.bookservice.domain.User;
import com.aivle06.bookservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
//    @GetMapping("/{user_id}")
//    public ResponseEntity<User>

    //Update PUT

    //Delete DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
