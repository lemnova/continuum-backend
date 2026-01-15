package tech.lemnova.continuum_backend.progress;

import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/progress")
public class DailyProgressController {

    private final DailyProgressService dailyProgressService;

    public DailyProgressController(DailyProgressService dailyProgressService) {
        this.dailyProgressService = dailyProgressService;
    }

    // GET /api/progress/habit/{habitId}?startDate=2024-01-01&endDate=2024-12-31
    @GetMapping("/habit/{habitId}")
    public ResponseEntity<List<DailyProgressDTO>> getProgressForHabit(
        @PathVariable String habitId,
        @RequestParam @DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE
        ) LocalDate startDate,
        @RequestParam @DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE
        ) LocalDate endDate
    ) {
        List<DailyProgressDTO> progress =
            dailyProgressService.getProgressForHabit(
                habitId,
                startDate,
                endDate
            );
        return ResponseEntity.ok(progress);
    }

    // GET /api/progress/user/{userId}?startDate=2024-01-01&endDate=2024-12-31
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DailyProgressDTO>> getProgressForUser(
        @PathVariable String userId,
        @RequestParam @DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE
        ) LocalDate startDate,
        @RequestParam @DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE
        ) LocalDate endDate
    ) {
        List<DailyProgressDTO> progress =
            dailyProgressService.getProgressForUser(userId, startDate, endDate);
        return ResponseEntity.ok(progress);
    }

    // GET /api/progress/user/{userId}/today
    @GetMapping("/user/{userId}/today")
    public ResponseEntity<List<DailyProgressDTO>> getTodayProgress(
        @PathVariable String userId
    ) {
        List<DailyProgressDTO> progress =
            dailyProgressService.getTodayProgressForUser(userId);
        return ResponseEntity.ok(progress);
    }

    // POST /api/progress/habit/{habitId}/toggle?date=2024-01-15
    @PostMapping("/habit/{habitId}/toggle")
    public ResponseEntity<DailyProgressDTO> toggleProgress(
        @PathVariable String habitId,
        @RequestParam @DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE
        ) LocalDate date
    ) {
        DailyProgressDTO progress = dailyProgressService.toggleProgress(
            habitId,
            date
        );
        return ResponseEntity.ok(progress);
    }

    // GET /api/progress/user/{userId}/score?date=2024-01-15
    @GetMapping("/user/{userId}/score")
    public ResponseEntity<Integer> getDailyScore(
        @PathVariable String userId,
        @RequestParam(required = false) @DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE
        ) LocalDate date
    ) {
        LocalDate targetDate = date != null ? date : LocalDate.now();
        Integer score = dailyProgressService.calculateDailyScore(
            userId,
            targetDate
        );
        return ResponseEntity.ok(score);
    }
}
