package com.example.learntodoback.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}