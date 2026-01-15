package tech.lemnova.continuum_backend.habit;

import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.lemnova.continuum_backend.habit.dtos.HabitCreateDTO;
import tech.lemnova.continuum_backend.habit.dtos.HabitDTO;
import tech.lemnova.continuum_backend.habit.dtos.HabitUpdateDTO;
import tech.lemnova.continuum_backend.habit.dtos.ProgressUpdateDTO;

@RestController
@RequestMapping("/api/habits")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    // GET /api/habits/user/{userId} (com paginação opcional)
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<HabitDTO>> listByUser(
        @PathVariable String userId,
        Pageable pageable
    ) {
        Page<HabitDTO> habits = habitService.listByUser(userId, pageable);
        return ResponseEntity.ok(habits);
    }

    // GET /api/habits/user/{userId}/all (sem paginação)
    @GetMapping("/user/{userId}/all")
    public ResponseEntity<List<HabitDTO>> listAllByUser(
        @PathVariable String userId
    ) {
        List<HabitDTO> habits = habitService.listByUser(userId);
        return ResponseEntity.ok(habits);
    }

    // POST /api/habits/user/{userId}
    @PostMapping("/user/{userId}")
    public ResponseEntity<HabitDTO> create(
        @PathVariable String userId,
        @Valid @RequestBody HabitCreateDTO dto
    ) {
        HabitDTO created = habitService.create(userId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/habits/{id}
    @GetMapping("/{id}")
    public ResponseEntity<HabitDTO> read(@PathVariable String id) {
        HabitDTO habit = habitService.read(id);
        return ResponseEntity.ok(habit);
    }

    // PUT /api/habits/{id}
    @PutMapping("/{id}")
    public ResponseEntity<HabitDTO> update(
        @PathVariable String id,
        @Valid @RequestBody HabitUpdateDTO dto
    ) {
        HabitDTO updated = habitService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/habits/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        habitService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // POST /api/habits/{id}/progress
    @PostMapping("/{id}/progress")
    public ResponseEntity<Void> updateProgress(
        @PathVariable String id,
        @Valid @RequestBody ProgressUpdateDTO dto
    ) {
        habitService.updateProgress(id, dto);
        return ResponseEntity.ok().build();
    }

    // GET /api/habits/{id}/streak
    @GetMapping("/{id}/streak")
    public ResponseEntity<Integer> getCurrentStreak(@PathVariable String id) {
        Integer streak = habitService.calculateCurrentStreak(id);
        return ResponseEntity.ok(streak);
    }
}
