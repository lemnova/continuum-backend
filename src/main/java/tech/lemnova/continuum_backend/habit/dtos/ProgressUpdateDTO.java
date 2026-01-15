package tech.lemnova.continuum_backend.habit.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record ProgressUpdateDTO(
    @NotNull(message = "Date is required") LocalDate date,

    @NotNull(message = "Completed status is required") Boolean completed,

    String notes
) {}
