package tech.lemnova.continuum_backend.habit;

import tech.lemnova.continuum_backend.habit.dtos.HabitUpdateDTO;
import tech.lemnova.continuum_backend.habit.dtos.HabitDTO;
import tech.lemnova.continuum_backend.habit.dtos.HabitCreateDTO;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/{userId}/habits")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<HabitDTO> create(
        @PathVariable Long userId,
        @RequestBody HabitCreateDTO dto
    ) {
        return ResponseEntity.ok(
            habitService.create(userId, dto)
        );
    }

    // LIST ALL (by user)
    @GetMapping
    public ResponseEntity<List<HabitDTO>> list(
        @PathVariable Long userId
    ) {
        return ResponseEntity.ok(
            habitService.listByUser(userId)
        );
    }

    // READ (single habit)
    @GetMapping("/{habitId}")
    public ResponseEntity<HabitDTO> read(
        @PathVariable Long habitId
    ) {
        return ResponseEntity.ok(
            habitService.read(habitId)
        );
    }

    // UPDATE
    @PutMapping("/{habitId}")
    public ResponseEntity<HabitDTO> update(
        @PathVariable Long habitId,
        @RequestBody HabitUpdateDTO dto
    ) {
        return ResponseEntity.ok(
            habitService.update(habitId, dto)
        );
    }

    // DELETE
    @DeleteMapping("/{habitId}")
    public ResponseEntity<Void> delete(
        @PathVariable Long habitId
    ) {
        habitService.delete(habitId);
        return ResponseEntity.noContent().build();
    }
}
