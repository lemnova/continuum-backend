package tech.lemnova.continuum_backend.dtos.habit;

import tech.lemnova.continuum_backend.entities.Habit;

public record HabitDTO(
    Long id,
    String name,
    String metadataJson,
    Boolean isActive
) {
    public static HabitDTO from(Habit habit) {
        return new HabitDTO(
            habit.getId(),
            habit.getName(),
            habit.getMetadataJson(),
            habit.getIsActive()
        );
    }
}
