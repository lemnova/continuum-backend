package tech.lemnova.continuum_backend.habit;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import tech.lemnova.continuum_backend.progress.DailyProgress;
import tech.lemnova.continuum_backend.user.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "habits")
public class Habit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String category;

    private String icon;

    private String color;

    @Column(nullable = false)
    private Boolean isActive = true;

    @CreationTimestamp
    private Instant createdAt;

    @UpdateTimestamp
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(
        mappedBy = "habit",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<DailyProgress> dailyProgress = new ArrayList<>();

    // Campo para soft delete (opcional)
    private Boolean deleted = false;
}
