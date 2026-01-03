package tech.lemnova.continuum_backend.dtos.habit;

import java.time.Instant;
import tech.lemnova.continuum_backend.entities.Habit;

public record HabitDTO(
    Long id,
    String name,
    String metadataJson,
    Boolean isActive,
    Instant createdAt,
    Instant updatedAt
) {
    public static HabitDTO from(Habit habit) {
        return new HabitDTO(
            habit.getId(),
            habit.getName(),
            habit.getMetadataJson(),
            habit.getIsActive(),
            habit.getCreatedAt(),
            habit.getUpdatedAt()
        );
    }
}
