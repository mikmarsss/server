package com.example.learntodoback.service;

import com.example.learntodoback.dto.ProjectDto;
import com.example.learntodoback.entity.Project;
import com.example.learntodoback.entity.User;
import com.example.learntodoback.exception.BusinessException;
import com.example.learntodoback.mapper.ProjectMapper;
import com.example.learntodoback.repository.ProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserService userService;
    private final ProjectMapper projectMapper;

    /**
     * Создание нового проекта для текущего авторизованного пользователя.
     */
    @Transactional
    public ProjectDto createProject(ProjectDto projectDto) {
        User user = userService.getCurrentUser();

        Project project = projectMapper.toEntity(projectDto);
        project.setUser(user);
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());

        Project savedProject = projectRepository.save(project);

        return projectMapper.toDto(savedProject);
    }

    /**
     * Получение всех проектов текущего пользователя.
     */
    public List<ProjectDto> getProjectsForCurrentUser() {
        User user = userService.getCurrentUser();
        List<Project> projects = projectRepository.findAllByUser(user);
        return projectMapper.toListDto(projects);
    }

    /**
     * Получение одного проекта по ID для текущего пользователя.
     */
    public ProjectDto getProjectById(Long projectId) {
        User user = userService.getCurrentUser();

        Project project = projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new BusinessException("Проект не найден"));

        return projectMapper.toDto(project);
    }

    /**
     * Обновление проекта для текущего пользователя.
     */
    @Transactional
    public ProjectDto updateProject(Long projectId, ProjectDto projectDto) {
        User user = userService.getCurrentUser();

        Project project = projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new BusinessException("Проект не найден"));

        project.setName(projectDto.getName());
        project.setUpdatedAt(LocalDateTime.now());

        Project updatedProject = projectRepository.save(project);

        return projectMapper.toDto(updatedProject);
    }


    /**
     * Удаление проекта по ID для текущего пользователя.
     */
    @Transactional
    public void deleteProject(Long projectId) {
        User user = userService.getCurrentUser();

        Project project = projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new BusinessException("Проект не найден"));

        projectRepository.delete(project);
    }
}
