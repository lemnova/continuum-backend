package tech.lemnova.continuum_backend.progress;

import java.time.Instant;
import java.time.LocalDate;

public record DailyProgressDTO(
    Long id,
    Long habitId,
    String habitName,
    LocalDate date,
    Boolean completed,
    String notes,
    Instant createdAt,
    Instant updatedAt
) {
    public static DailyProgressDTO from(DailyProgress progress) {
        return new DailyProgressDTO(
            progress.getId(),
            progress.getHabit().getId(),
            progress.getHabit().getName(),
            progress.getDate(),
            progress.getCompleted(),
            progress.getNotes(),
            progress.getCreatedAt(),
            progress.getUpdatedAt()
        );
    }
}
