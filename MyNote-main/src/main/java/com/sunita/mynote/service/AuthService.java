package com.sunita.mynote.service;

import com.sunita.mynote.dto.auth.LoginRequest;
import com.sunita.mynote.dto.auth.LoginResponse;
import com.sunita.mynote.dto.auth.RegisterRequest;
import com.sunita.mynote.model.User;
import com.sunita.mynote.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(RegisterRequest request) {
        // Create a new User entity from request
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // Password should be hashed in real apps

        // Save the user to the database
        userRepository.save(user);
    }

    public LoginResponse loginUser(LoginRequest request) {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(request.getPassword())) {
                return new LoginResponse(user.getId(), user.getUsername(), user.getEmail(), "Login successful");
            }
        }
        return new LoginResponse(null, null, null, "Invalid username or password");
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
