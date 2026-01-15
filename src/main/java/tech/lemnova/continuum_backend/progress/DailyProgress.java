package tech.lemnova.continuum_backend.progress;

import jakarta.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tech.lemnova.continuum_backend.habit.Habit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
    name = "daily_progress",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = { "habit_id", "date" }),
    },
    indexes = {
        @Index(name = "idx_habit_date", columnList = "habit_id, date"),
        @Index(name = "idx_date", columnList = "date"),
    }
)
public class DailyProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "habit_id", nullable = false)
    private Habit habit;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Boolean completed = false;

    @Column(length = 500)
    private String notes;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;
}
