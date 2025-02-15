package com.example.learntodoback.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterResponseDto {
    private Long id;
    private String username;
    private String email;
    private String token;
}
