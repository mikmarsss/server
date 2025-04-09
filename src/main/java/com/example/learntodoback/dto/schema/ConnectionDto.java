package com.example.learntodoback.dto.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionDto {
    private String uniqueElementId;
    private int sourceElementId;
    private int targetElementId;
    private String sourceContactId;
    private String targetContactId;
}