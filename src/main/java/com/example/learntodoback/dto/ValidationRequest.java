package com.example.learntodoback.dto;

import com.example.learntodoback.dto.schema.ConnectionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationRequest {
    private List<ConnectionDto> connections;
}
