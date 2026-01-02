package tech.lemnova.continuum_backend.dtos.habit;

import java.time.LocalDate;

public class HabitCreateDTO {
    public String name;
    public LocalDate initDate;
    public String metadataJson;
}
