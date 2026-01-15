package tech.lemnova.continuum_backend.habit;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HabitRepository extends JpaRepository<Habit, Long> {
    List<Habit> findAllByUserId(Long userId);

    Page<Habit> findAllByUserId(Long userId, Pageable pageable);

    List<Habit> findAllByUserIdAndIsActiveTrue(Long userId);

    Page<Habit> findAllByUserIdAndIsActiveTrue(Long userId, Pageable pageable);

    @Query(
        "SELECT h FROM Habit h WHERE h.user.id = :userId AND h.deleted = false"
    )
    List<Habit> findAllActiveByUserId(@Param("userId") Long userId);

    @Query(
        "SELECT h FROM Habit h WHERE h.user.id = :userId AND h.category = :category AND h.deleted = false"
    )
    List<Habit> findAllByUserIdAndCategory(
        @Param("userId") Long userId,
        @Param("category") String category
    );
}
