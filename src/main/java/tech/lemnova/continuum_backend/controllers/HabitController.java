package tech.lemnova.continuum_backend.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.lemnova.continuum_backend.dtos.habit.*;
import tech.lemnova.continuum_backend.services.HabitService;

@RestController
@RequestMapping("/habits")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    // CREATE
    @PostMapping("/user/{userId}")
    public ResponseEntity<HabitDTO> create(
        @PathVariable Long userId,
        @RequestBody HabitCreateDTO dto
    ) {
        HabitDTO habit = habitService.create(userId, dto);
        return ResponseEntity.ok(habit);
    }

    // READ
    @GetMapping("/{id}")
    public ResponseEntity<HabitDTO> read(
        @PathVariable Long id
    ) {
        HabitDTO habit = habitService.read(id);
        return ResponseEntity.ok(habit);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<HabitDTO> update(
        @PathVariable Long id,
        @RequestBody HabitUpdateDTO dto
    ) {
        HabitDTO habit = habitService.update(id, dto);
        return ResponseEntity.ok(habit);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @PathVariable Long id
    ) {
        habitService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
