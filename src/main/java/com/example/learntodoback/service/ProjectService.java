package com.example.learntodoback.service;

import com.example.learntodoback.dto.ProjectDto;
import com.example.learntodoback.entity.Project;
import com.example.learntodoback.entity.User;
import com.example.learntodoback.exception.BusinessException;
import com.example.learntodoback.mapper.ProjectMapper;
import com.example.learntodoback.repository.ProjectRepository;
import com.example.learntodoback.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final ProjectMapper projectMapper;

    /**
     * Создание нового проекта для пользователя по ID.
     */
    @Transactional
    public ProjectDto createProject(Long userId, ProjectDto projectDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Пользователь не найден"));

        Project project = projectMapper.toEntity(projectDto);
        project.setUser(user);
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        project.setData(projectDto.getData());

        Project savedProject = projectRepository.save(project);

        return projectMapper.toDto(savedProject);
    }

    /**
     * Получение всех проектов пользователя по ID.
     */
    public List<ProjectDto> getProjectsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Пользователь не найден"));

        List<Project> projects = projectRepository.findAllByUser(user);
        return projectMapper.toListDto(projects);
    }

    /**
     * Получение одного проекта по ID для пользователя.
     */
    public ProjectDto getProjectById(Long userId, Long projectId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Пользователь не найден"));

        Project project = projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new BusinessException("Проект не найден"));

        return projectMapper.toDto(project);
    }

    /**
     * Обновление проекта для пользователя по ID.
     */
    @Transactional
    public ProjectDto updateProject(Long userId, Long projectId, ProjectDto projectDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Пользователь не найден"));

        Project project = projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new BusinessException("Проект не найден"));

        project.setName(projectDto.getName());
        project.setUpdatedAt(LocalDateTime.now());
        project.setData(projectDto.getData());

        Project updatedProject = projectRepository.save(project);

        return projectMapper.toDto(updatedProject);
    }

    /**
     * Удаление проекта по ID для пользователя.
     */
    @Transactional
    public void deleteProject(Long userId, Long projectId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Пользователь не найден"));

        Project project = projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new BusinessException("Проект не найден"));

        projectRepository.delete(project);
    }
}
