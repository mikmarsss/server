package com.example.learntodoback.controller;

import com.example.learntodoback.dto.request.ProjectRequestDto;
import com.example.learntodoback.dto.response.ProjectResponseDto;
import com.example.learntodoback.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createProject(@PathVariable Long userId) {
        ProjectResponseDto result = projectService.createProject(userId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/all/{userId}")
    public ResponseEntity<?> getProjectsForUser(@PathVariable Long userId) {
        List<ProjectResponseDto> projectResponseDtos = projectService.getProjectsForUser(userId);
        return ResponseEntity.ok(projectResponseDtos);
    }

    @GetMapping("/one/{userId}/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable Long userId, @PathVariable Long projectId) {
        ProjectResponseDto result = projectService.getProjectById(userId, projectId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{userId}/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable Long userId, @PathVariable Long projectId,
            @RequestBody ProjectRequestDto projectRequestDto) {
        ProjectResponseDto updatedProject = projectService.updateProject(userId, projectId, projectRequestDto);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/delete/{userId}/{projectId}")
    public void deleteProject(@PathVariable Long userId, @PathVariable Long projectId) {
        projectService.deleteProject(userId, projectId);
    }
}