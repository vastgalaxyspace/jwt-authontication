package com.example.demo.Services;

import com.example.demo.Entity.User;
import com.example.demo.Repository.Userrepository;
import com.example.demo.Security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Authservices {

    private final Userrepository userrepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public String register(User user) {
        if (userrepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userrepository.save(user);
        return "User saved successfully";
    }

    public String login(String email, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password));

        if (auth.isAuthenticated()) {
            User user = userrepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            return jwtUtil.generateToken(user.getEmail(), user.getRole());
        }

        throw new RuntimeException("Invalid credentials");
    }
}
