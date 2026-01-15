package tech.lemnova.continuum_backend.progress;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyProgressRepository
    extends JpaRepository<DailyProgress, Long>
{
    Optional<DailyProgress> findByHabitIdAndDate(Long habitId, LocalDate date);

    List<DailyProgress> findByHabitIdAndDateBetween(
        Long habitId,
        LocalDate startDate,
        LocalDate endDate
    );

    @Query(
        "SELECT dp FROM DailyProgress dp WHERE dp.habit.user.id = :userId AND dp.date = :date"
    )
    List<DailyProgress> findByUserIdAndDate(
        @Param("userId") Long userId,
        @Param("date") LocalDate date
    );

    @Query(
        "SELECT dp FROM DailyProgress dp WHERE dp.habit.user.id = :userId AND dp.date BETWEEN :startDate AND :endDate"
    )
    List<DailyProgress> findByUserIdAndDateBetween(
        @Param("userId") Long userId,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    @Query(
        "SELECT COUNT(dp) FROM DailyProgress dp WHERE dp.habit.id = :habitId AND dp.completed = true"
    )
    Long countCompletedByHabitId(@Param("habitId") Long habitId);
}
