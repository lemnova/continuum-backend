package tech.lemnova.continuum_backend.progress;

import java.time.Instant;
import java.time.LocalDate;

public record DailyProgressDTO(
    String id,
    String habitId,
    String userId,
    LocalDate date,
    Boolean completed,
    String notes,
    Instant createdAt,
    Instant updatedAt
) {
    public static DailyProgressDTO from(DailyProgress progress) {
        return new DailyProgressDTO(
            progress.getId(),
            progress.getHabitId(),
            progress.getUserId(),
            progress.getDate(),
            progress.getCompleted(),
            progress.getNotes(),
            progress.getCreatedAt(),
            progress.getUpdatedAt()
        );
    }
}
