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
    private String uniqueSourceElementId;
    private String uniqueTargetElementId;
    private int sourceElementId;
    private int targetElementId;
    private SourceContactDto sourceContact;
    private TargetContactDto targetContact;
}