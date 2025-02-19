package com.example.learntodoback.mapper.request;

import com.example.learntodoback.dto.request.ProjectRequestDto;
import com.example.learntodoback.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectRequestMapper {
    ProjectRequestDto toDto(Project project);

    Project toEntity(ProjectRequestDto projectRequestDto);

    List<Project> toListEntity(List<ProjectRequestDto> projectRequestDtos);

    List<ProjectRequestDto> toListDto(List<Project> projects);

}
