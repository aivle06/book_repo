package com.aivle06.bookservice.service;

import com.aivle06.bookservice.domain.User;
import com.aivle06.bookservice.exception.ResourceNotFoundException;
import com.aivle06.bookservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    //CRUD
    //Create
    @Override
    public User createUser(User user){
        return userRepository.save(user);
    }

    //Read
    @Override
    @Transactional(readOnly=true)
    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException("사용자가 존재하지 않습니다"));
    }
    //Update
    @Override
    public User updateUser(Long id, User user){
        User u = getUserById(id);
        return userRepository.save(u);
    }

    //Delete
    @Override
    public void deleteUser(Long id){
        User u = getUserById(id);
        userRepository.delete(u);
    }
}
