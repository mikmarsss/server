package com.example.learntodoback.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResponseDto {
    private boolean isValid;
    private String message;
    private List<String> uniqueId;
    private String additional;
}
