package tech.lemnova.continuum_backend.progress;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyProgressRepository
    extends MongoRepository<DailyProgress, String>
{
    Optional<DailyProgress> findByHabitIdAndDate(
        String habitId,
        LocalDate date
    );

    List<DailyProgress> findByHabitIdAndDateBetween(
        String habitId,
        LocalDate startDate,
        LocalDate endDate
    );

    List<DailyProgress> findByUserIdAndDate(String userId, LocalDate date);

    List<DailyProgress> findByUserIdAndDateBetween(
        String userId,
        LocalDate startDate,
        LocalDate endDate
    );

    @Query(value = "{'habitId': ?0, 'completed': true}", count = true)
    Long countCompletedByHabitId(String habitId);
}
