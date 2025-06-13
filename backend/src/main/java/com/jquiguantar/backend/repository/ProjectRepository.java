package com.jquiguantar.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jquiguantar.backend.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    
}
