package com.aivle06.bookservice.service;

import com.aivle06.bookservice.domain.User;

public interface UserService {

    //CRUD
    //Create
    User createUser(User user);

    //Read
    User getUserById(Long id);

    //Update
    User updateUser(Long id, User user);

    //Delete
    void deleteUser(Long id);
}
