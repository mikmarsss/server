package com.example.learntodoback.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ProjectResponseDto {
    private Long id;
    private String name;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String data;
}
