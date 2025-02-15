package com.example.learntodoback.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthResponseDto {
    private Long id;
    private String username;
    private String token;
}
