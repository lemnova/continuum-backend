package tech.lemnova.continuum_backend.services;

import org.springframework.stereotype.Service;
import tech.lemnova.continuum_backend.dtos.HabitCreateDTO;
import tech.lemnova.continuum_backend.entities.*;
import tech.lemnova.continuum_backend.repositories.*;

@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    public HabitService(
        HabitRepository habitRepository,
        UserRepository userRepository
    ) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
    }

    public Habit createHabit(Long userId, HabitCreateDTO dto) {
        User user = userRepository
            .findById(userId)
            .orElseThrow(() -> new RuntimeException("User not founded!"));

        Habit habit = new Habit();
        habit.setName(dto.name);
        habit.setInitDate(dto.initDate);
        habit.setMetadataJson(dto.metadataJson);
        habit.setProgressJson("{}");
        habit.setUser(user);

        return habitRepository.save(habit);
    }
}
