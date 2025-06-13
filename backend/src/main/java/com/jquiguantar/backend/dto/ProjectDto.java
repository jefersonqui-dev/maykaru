package com.jquiguantar.backend.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {
    private Long id;
    @NotBlank(message = "Title is mandatory")
    private String title;
    private String description;
    private String imageUrl;
    private String githubUrl;
    private String demoUrl;
    private String technologies;
}
