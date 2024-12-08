package com.event_management.management.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.event_management.management.dto.LoginRequestDto;
import com.event_management.management.dto.RegisterRequestDto;
import com.event_management.management.helpers.ResponseHelper;
import com.event_management.management.model.User;
import com.event_management.management.repository.UserRepository;
import com.event_management.management.service.AuthService;
import com.event_management.management.util.JwtUtil;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthService authService;

    @GetMapping("/health-check")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Ok");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequestDto body) {
        try {
            Optional<User> user = userRepository.findByUsername(body.getUsername());

            if (user.isEmpty() || !new BCryptPasswordEncoder().matches(body.getPassword(), user.get().getPasswordHash())) {
                return ResponseHelper.createErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid Credentials",false,null);
            }
            String token = jwtUtil.generateToken(user.get().getUsername(), user.get().getRole(),user.get().getEmail(),user.get().getUserId());
            return ResponseHelper.createResponse(HttpStatus.OK, "Login successful", token,null);

        } catch (Exception e) {
            return ResponseHelper.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),false,null);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegisterRequestDto userRequestDto){
        try{
            User registerUser = authService.registerEntity(userRequestDto);
            User data = userRepository.save(registerUser);
//            user.setPasswordHash(new BCryptPasswordEncoder().encode(user.getPasswordHash()));
//            userRepository.save(user);
            return ResponseHelper.createResponse(HttpStatus.OK, "Successfully registered", data,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(),false,null);
        }
    }
    @GetMapping("/user/all")
    public ResponseEntity<Object> getAllUsers(@PathVariable String eventId) {
        try{
            List<User> data =  authService.getAllUsers();
            return ResponseHelper.createResponse(HttpStatus.OK,"Users retrieved successfully",data,null);
        }catch (Exception e){
            return ResponseHelper.createErrorResponse(HttpStatus.BAD_REQUEST,e.getMessage(),false,null);
        }
    }
}
