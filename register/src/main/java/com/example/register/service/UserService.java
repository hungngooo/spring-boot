package com.example.register.service;

import com.example.register.model.User;
import com.example.register.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    public List<User> findByNameContaining(String name){
        return userRepository.findByNameContaining(name);
    }
    public User updateUser(User user) {
        return userRepository.save(user);
    }

}
