package com.example.learntodoback.mapper;

import com.example.learntodoback.dto.ProjectDto;
import com.example.learntodoback.entity.Project;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {
    @Mapping(source = "user.id", target = "userId")
    ProjectDto toDto(Project project);

    @Mapping(source = "userId", target = "user.id")
    Project toEntity(ProjectDto projectDto);

    List<Project> toListEntity(List<ProjectDto> projectDtos);

    List<ProjectDto> toListDto(List<Project> projects);

}
