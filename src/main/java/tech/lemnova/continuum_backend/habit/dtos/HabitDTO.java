package tech.lemnova.continuum_backend.habit.dtos;

import java.time.Instant;
import tech.lemnova.continuum_backend.habit.Habit;

public record HabitDTO(
    String id,
    String name,
    String description,
    String category,
    String icon,
    String color,
    Boolean isActive,
    Instant createdAt,
    Instant updatedAt,
    String userId,
    Integer currentStreak,
    Long totalCompletions
) {
    public static HabitDTO from(Habit habit) {
        return new HabitDTO(
            habit.getId(),
            habit.getName(),
            habit.getDescription(),
            habit.getCategory(),
            habit.getIcon(),
            habit.getColor(),
            habit.getIsActive(),
            habit.getCreatedAt(),
            habit.getUpdatedAt(),
            habit.getUserId(),
            null,
            null
        );
    }

    public static HabitDTO from(
        Habit habit,
        Integer currentStreak,
        Long totalCompletions
    ) {
        return new HabitDTO(
            habit.getId(),
            habit.getName(),
            habit.getDescription(),
            habit.getCategory(),
            habit.getIcon(),
            habit.getColor(),
            habit.getIsActive(),
            habit.getCreatedAt(),
            habit.getUpdatedAt(),
            habit.getUserId(),
            currentStreak,
            totalCompletions
        );
    }
}
