package tech.lemnova.continuum_backend.habit;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository extends MongoRepository<Habit, String> {
    List<Habit> findAllByUserId(String userId);

    Page<Habit> findAllByUserId(String userId, Pageable pageable);

    List<Habit> findAllByUserIdAndIsActiveTrue(String userId);

    Page<Habit> findAllByUserIdAndIsActiveTrue(
        String userId,
        Pageable pageable
    );

    @Query("{'userId': ?0, 'deleted': false}")
    List<Habit> findAllActiveByUserId(String userId);

    @Query("{'userId': ?0, 'category': ?1, 'deleted': false}")
    List<Habit> findAllByUserIdAndCategory(String userId, String category);
}
