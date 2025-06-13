package com.jquiguantar.backend.controller;

import com.jquiguantar.backend.dto.ProjectDto;
import com.jquiguantar.backend.entity.Project;
import com.jquiguantar.backend.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils; // Para copiar propiedades
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // Para proteger endpoints
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Mapper simple de Entity a DTO
    private ProjectDto convertToDto(Project project) {
        ProjectDto projectDto = new ProjectDto();
        BeanUtils.copyProperties(project, projectDto);
        return projectDto;
    }

    // Mapper simple de DTO a Entity
    private Project convertToEntity(ProjectDto projectDto) {
        Project project = new Project();
        BeanUtils.copyProperties(projectDto, project);
        return project;
    }

    // Endpoint público: Obtener todos los proyectos
    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProjects() {
        List<Project> projects = projectService.getAllProjects();
        List<ProjectDto> projectDtos = projects.stream()
                                                .map(this::convertToDto)
                                                .collect(Collectors.toList());
        return ResponseEntity.ok(projectDtos);
    }

    // Endpoint público: Obtener un proyecto por ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDto> getProjectById(@PathVariable Long id) {
        Project project = projectService.getProjectById(id)
                                        .orElseThrow(() -> new RuntimeException("Project not found with id: " + id)); // Mejor usar una ResourceNotFoundException personalizada
        return ResponseEntity.ok(convertToDto(project));
    }

    // Endpoint protegido (solo para admin): Crear un nuevo proyecto
    @PreAuthorize("hasRole('ADMIN')") // Solo usuarios con rol ADMIN pueden acceder
    @PostMapping
    public ResponseEntity<ProjectDto> createProject(@Valid @RequestBody ProjectDto projectDto) {
        Project project = convertToEntity(projectDto);
        Project createdProject = projectService.createProject(project);
        return new ResponseEntity<>(convertToDto(createdProject), HttpStatus.CREATED);
    }

    // Endpoint protegido (solo para admin): Actualizar un proyecto
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable Long id, @Valid @RequestBody ProjectDto projectDto) {
        Project projectDetails = convertToEntity(projectDto);
        Project updatedProject = projectService.updateProject(id, projectDetails);
        return ResponseEntity.ok(convertToDto(updatedProject));
    }

    // Endpoint protegido (solo para admin): Eliminar un proyecto
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}