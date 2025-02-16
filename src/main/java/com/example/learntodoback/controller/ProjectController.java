package com.example.learntodoback.controller;

import com.example.learntodoback.dto.ProjectDto;
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

    @PostMapping("/user/{userId}")
    public ResponseEntity<?> createProject(@PathVariable Long userId, @RequestBody ProjectDto projectDto) {
        ProjectDto result = projectService.createProject(userId, projectDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getProjectsForUser(@PathVariable Long userId) {
        List<ProjectDto> projectDtos = projectService.getProjectsForUser(userId);
        return ResponseEntity.ok(projectDtos);
    }

    @GetMapping("/user/{userId}/{projectId}")
    public ResponseEntity<?> getProjectById(@PathVariable Long userId, @PathVariable Long projectId) {
        ProjectDto result = projectService.getProjectById(userId, projectId);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/user/{userId}/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable Long userId, @PathVariable Long projectId, @RequestBody ProjectDto projectDto) {
        ProjectDto updatedProject = projectService.updateProject(userId, projectId, projectDto);
        return ResponseEntity.ok(updatedProject);
    }

    @DeleteMapping("/user/{userId}/{projectId}")
    public void deleteProject(@PathVariable Long userId, @PathVariable Long projectId) {
        projectService.deleteProject(userId, projectId);
    }
}