package tech.lemnova.continuum_backend.habit.dtos;

import jakarta.validation.constraints.Size;

public record HabitUpdateDTO(
    @Size(
        min = 2,
        max = 100,
        message = "Name must be between 2 and 100 characters"
    )
    String name,

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    String description,

    @Size(max = 50, message = "Category cannot exceed 50 characters")
    String category,

    String icon,

    String color,

    Boolean isActive
) {}
