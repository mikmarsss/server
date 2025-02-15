package com.example.learntodoback.repository;

import com.example.learntodoback.entity.Project;
import com.example.learntodoback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByUser(User user);

    Optional<Project> findByIdAndUser(Long projectId, User user);

}
