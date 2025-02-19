package com.example.learntodoback.service;

import com.example.learntodoback.dto.request.ProjectRequestDto;
import com.example.learntodoback.dto.response.ProjectResponseDto;
import com.example.learntodoback.entity.Project;
import com.example.learntodoback.entity.User;
import com.example.learntodoback.exception.BusinessException;
import com.example.learntodoback.mapper.request.ProjectRequestMapper;
import com.example.learntodoback.mapper.response.ProjectResponseMapper;
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
    private final ProjectRequestMapper projectRequestMapper;
    private final ProjectResponseMapper projectResponseMapper;

    /**
     * Создание нового проекта для пользователя по ID.
     */
    @Transactional
    public ProjectResponseDto createProject(Long userId, ProjectRequestDto projectRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Пользователь не найден"));

        Project project = projectRequestMapper.toEntity(projectRequestDto);
        project.setUser(user);
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        project.setData(projectRequestDto.getData());

        Project savedProject = projectRepository.save(project);

        return projectResponseMapper.toDto(savedProject);
    }

    /**
     * Получение всех проектов пользователя по ID.
     */
    public List<ProjectResponseDto> getProjectsForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Пользователь не найден"));

        List<Project> projects = projectRepository.findAllByUser(user);
        return projectResponseMapper.toListDto(projects);
    }

    /**
     * Получение одного проекта по ID для пользователя.
     */
    public ProjectResponseDto getProjectById(Long userId, Long projectId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Пользователь не найден"));

        Project project = projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new BusinessException("Проект не найден"));

        return projectResponseMapper.toDto(project);
    }

    /**
     * Обновление проекта для пользователя по ID.
     */
    @Transactional
    public ProjectResponseDto updateProject(Long userId, Long projectId, ProjectRequestDto projectRequestDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Пользователь не найден"));

        Project project = projectRepository.findByIdAndUser(projectId, user)
                .orElseThrow(() -> new BusinessException("Проект не найден"));

        project.setName(projectRequestDto.getName());
        project.setUpdatedAt(LocalDateTime.now());
        project.setData(projectRequestDto.getData());

        Project updatedProject = projectRepository.save(project);

        return projectResponseMapper.toDto(updatedProject);
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
