package com.example.learntodoback.controller;

import com.example.learntodoback.dto.auth.AuthRequestDto;
import com.example.learntodoback.dto.auth.AuthResponseDto;
import com.example.learntodoback.dto.auth.RegisterRequestDto;
import com.example.learntodoback.dto.auth.RegisterResponseDto;
import com.example.learntodoback.service.AuthService;
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
    public ResponseEntity<?> register(@RequestBody RegisterRequestDto request) {
        RegisterResponseDto token = authService.register(request);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto request) {
        AuthResponseDto token = authService.login(request);
        return ResponseEntity.ok(token);
    }
}
