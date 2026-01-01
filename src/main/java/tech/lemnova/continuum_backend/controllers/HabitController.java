package tech.lemnova.continuum_backend.controllers;

import org.springframework.web.bind.annotation.*;
import tech.lemnova.continuum_backend.dtos.HabitCreateDTO;
import tech.lemnova.continuum_backend.entities.Habit;
import tech.lemnova.continuum_backend.services.HabitService;

@RestController
@RequestMapping("/habits")
public class HabitController {

    private final HabitService habitService;

    public HabitController(HabitService habitService) {
        this.habitService = habitService;
    }

    @PostMapping("/user/{userId}")
    public Habit createHabit(
        @PathVariable Long userId,
        @RequestBody HabitCreateDTO dto
    ) {
        return habitService.createHabit(userId, dto);
    }
}
