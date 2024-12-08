package com.event_management.management.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.event_management.management.dto.RegisterRequestDto;
import com.event_management.management.model.User;
import com.event_management.management.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User registerEntity(RegisterRequestDto dto){
        try{
            Optional<User> existingUser = userRepository.findByUsername(dto.getUsername());
            if(existingUser.isPresent()) {
                throw new RuntimeException("Username already exists");
            }

            User user = new User();
            user.setUsername(dto.getUsername());
            user.setPasswordHash(encodePassword(dto.getPassword()));
            user.setEmail(dto.getEmail());
            user.setPhoneNumber(dto.getPhoneNumber());
            user.setRole(dto.getRole());
            return user;
        }catch (Exception e){
            throw e;
        }
    }

    public String encodePassword(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

//    public User loginEntity(LoginRequestDto dto){
//        User user = new User();
//        user.setUsername(dto.getUsername());
////        user.setPasswordHash();
//    }
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}
