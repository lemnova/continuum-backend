package tech.lemnova.continuum_backend.user;

import jakarta.persistence.*;
import java.util.List;
import lombok.*;
import tech.lemnova.continuum_backend.habit.Habit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String role = "User";
    
    @Column(nullable = false)
    private String emailVerified;
    
    @Column(nullable = false)
    private Boolean isActive = false;

    @OneToMany(
        mappedBy = "user",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<Habit> Habits;
}
