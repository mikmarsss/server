package com.example.learntodoback.controller;

import com.example.learntodoback.dto.auth.AuthRequestDto;
import com.example.learntodoback.dto.auth.AuthResponseDto;
import com.example.learntodoback.dto.auth.RegisterRequestDto;
import com.example.learntodoback.dto.auth.RegisterResponseDto;
import com.example.learntodoback.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto request, HttpServletResponse response) {
        RegisterResponseDto token = authService.register(request, response);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto request, HttpServletResponse response) {
        AuthResponseDto token = authService.login(request, response);
        return ResponseEntity.ok(token);
    }
}
