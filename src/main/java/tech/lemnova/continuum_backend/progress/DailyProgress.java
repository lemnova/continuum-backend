package tech.lemnova.continuum_backend.progress;

import java.time.Instant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "daily_progress")
@CompoundIndex(
    name = "habit_date_idx",
    def = "{'habitId': 1, 'date': 1}",
    unique = true
)
public class DailyProgress {

    @Id
    private String id;

    @Indexed
    private String habitId;

    @Indexed
    private String userId;

    @Indexed
    private LocalDate date;

    private Boolean completed = false;

    private String notes;

    private Instant createdAt = Instant.now();

    private Instant updatedAt = Instant.now();
}
