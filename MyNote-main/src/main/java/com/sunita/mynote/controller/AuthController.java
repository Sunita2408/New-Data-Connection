package com.sunita.mynote.controller;

import com.sunita.mynote.dto.auth.LoginRequest;
import com.sunita.mynote.dto.auth.LoginResponse;
import com.sunita.mynote.dto.auth.RegisterRequest;
import com.sunita.mynote.model.User;
import com.sunita.mynote.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService userService;

    // Register a new user
    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        userService.registerUser(request);
        return ResponseEntity.ok("User registered successfully");
    }

    // Login a user
    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = userService.loginUser(request);

        if (response.getUserId() != null) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


    @CrossOrigin(origins = "http://127.0.0.1:5501")
    @GetMapping("/allUsers")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

}
