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
    @GetMapping("/{user_id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    //Update PUT
    @PutMapping("/{user_id}")
    public ResponseEntity<User> updateUser(
            @PathVariable Long id, User user
    ){
        User updatedUser = userService.updateUser(id, user);
        return ResponseEntity.ok(updatedUser);
    }

    //Delete DELETE
    @DeleteMapping("/{user_id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
