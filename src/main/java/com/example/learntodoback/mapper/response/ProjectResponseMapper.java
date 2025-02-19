package com.example.learntodoback.mapper.response;

import com.example.learntodoback.dto.response.ProjectResponseDto;
import com.example.learntodoback.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectResponseMapper {
    @Mapping(source = "user.id", target = "userId")
    ProjectResponseDto toDto(Project project);

    @Mapping(source = "userId", target = "user.id")
    Project toEntity(ProjectResponseDto projectDto);

    List<Project> toListEntity(List<ProjectResponseDto> projectDtos);

    List<ProjectResponseDto> toListDto(List<Project> projects);

}

