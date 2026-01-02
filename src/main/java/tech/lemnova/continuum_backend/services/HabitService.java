package tech.lemnova.continuum_backend.services;

import tech.lemnova.continuum_backend.dtos.habit.HabitUpdateDTO;
import tech.lemnova.continuum_backend.dtos.habit.HabitDTO;
import tech.lemnova.continuum_backend.dtos.habit.HabitCreateDTO;
import org.springframework.stereotype.Service;
import tech.lemnova.continuum_backend.dtos.*;
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

    // CREATE
    public HabitDTO create(Long userId, HabitCreateDTO dto) {
        User user = userRepository
            .findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Habit habit = new Habit();
        habit.setName(dto.name);
        habit.setInitDate(dto.initDate);
        habit.setMetadataJson(dto.metadataJson);
        habit.setProgressJson("{}");
        habit.setIsActive(true);
        habit.setUser(user);

        Habit saved = habitRepository.save(habit);
        return HabitDTO.from(saved);
    }

    // READ
    public HabitDTO read(Long id) {
        Habit habit = habitRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Habit not found"));

        return HabitDTO.from(habit);
    }

    // UPDATE
    public HabitDTO update(Long id, HabitUpdateDTO dto) {
        Habit habit = habitRepository
            .findById(id)
            .orElseThrow(() -> new RuntimeException("Habit not found"));

        if (dto.name != null) {
            habit.setName(dto.name);
        }

        if (dto.metadataJson != null) {
            habit.setMetadataJson(dto.metadataJson);
        }

        if (dto.isActive != null) {
            habit.setIsActive(dto.isActive);
        }

        Habit updated = habitRepository.saveAndFlush(habit);
        return HabitDTO.from(updated);
    }

    // DELETE
    public void delete(Long id) {
        if (!habitRepository.existsById(id)) {
            throw new RuntimeException("Habit not found");
        }
        habitRepository.deleteById(id);
    }
}
