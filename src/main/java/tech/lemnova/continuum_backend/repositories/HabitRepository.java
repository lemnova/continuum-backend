package tech.lemnova.continuum_backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tech.lemnova.continuum_backend.entities.Habit;

public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findByUserId(Long userId);
}
