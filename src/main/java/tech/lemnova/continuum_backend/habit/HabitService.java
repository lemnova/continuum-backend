package tech.lemnova.continuum_backend.habit;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.lemnova.continuum_backend.exception.ResourceNotFoundException;
import tech.lemnova.continuum_backend.habit.dtos.HabitCreateDTO;
import tech.lemnova.continuum_backend.habit.dtos.HabitDTO;
import tech.lemnova.continuum_backend.habit.dtos.HabitUpdateDTO;
import tech.lemnova.continuum_backend.habit.dtos.ProgressUpdateDTO;
import tech.lemnova.continuum_backend.progress.DailyProgress;
import tech.lemnova.continuum_backend.progress.DailyProgressRepository;
import tech.lemnova.continuum_backend.user.User;
import tech.lemnova.continuum_backend.user.UserRepository;

@Service
public class HabitService {

    private final HabitRepository habitRepository;
    private final UserRepository userRepository;
    private final DailyProgressRepository dailyProgressRepository;

    public HabitService(
        HabitRepository habitRepository,
        UserRepository userRepository,
        DailyProgressRepository dailyProgressRepository
    ) {
        this.habitRepository = habitRepository;
        this.userRepository = userRepository;
        this.dailyProgressRepository = dailyProgressRepository;
    }

    // LIST BY USER (com paginação)
    public Page<HabitDTO> listByUser(String userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                "User not found with id: " + userId
            );
        }

        return habitRepository
            .findAllByUserId(userId, pageable)
            .map(habit -> {
                Integer streak = calculateCurrentStreak(habit.getId());
                Long total = dailyProgressRepository.countCompletedByHabitId(
                    habit.getId()
                );
                return HabitDTO.from(habit, streak, total);
            });
    }

    // LIST BY USER (sem paginação)
    public List<HabitDTO> listByUser(String userId) {
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                "User not found with id: " + userId
            );
        }

        return habitRepository
            .findAllByUserId(userId)
            .stream()
            .map(habit -> {
                Integer streak = calculateCurrentStreak(habit.getId());
                Long total = dailyProgressRepository.countCompletedByHabitId(
                    habit.getId()
                );
                return HabitDTO.from(habit, streak, total);
            })
            .collect(Collectors.toList());
    }

    // CREATE
    @Transactional
    public HabitDTO create(String userId, HabitCreateDTO dto) {
        User user = userRepository
            .findById(userId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "User not found with id: " + userId
                )
            );

        Habit habit = new Habit();
        habit.setName(dto.name());
        habit.setDescription(dto.description());
        habit.setCategory(dto.category());
        habit.setIcon(dto.icon());
        habit.setColor(dto.color());
        habit.setIsActive(true);
        habit.setDeleted(false);
        habit.setUserId(user.getId());
        habit.setCreatedAt(Instant.now());
        habit.setUpdatedAt(Instant.now());

        Habit saved = habitRepository.save(habit);
        return HabitDTO.from(saved, 0, 0L);
    }

    // READ
    public HabitDTO read(String id) {
        Habit habit = habitRepository
            .findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Habit not found with id: " + id)
            );

        Integer streak = calculateCurrentStreak(id);
        Long total = dailyProgressRepository.countCompletedByHabitId(id);

        return HabitDTO.from(habit, streak, total);
    }

    // UPDATE
    @Transactional
    public HabitDTO update(String id, HabitUpdateDTO dto) {
        Habit habit = habitRepository
            .findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Habit not found with id: " + id)
            );

        if (dto.name() != null) {
            habit.setName(dto.name());
        }

        if (dto.description() != null) {
            habit.setDescription(dto.description());
        }

        if (dto.category() != null) {
            habit.setCategory(dto.category());
        }

        if (dto.icon() != null) {
            habit.setIcon(dto.icon());
        }

        if (dto.color() != null) {
            habit.setColor(dto.color());
        }

        if (dto.isActive() != null) {
            habit.setIsActive(dto.isActive());
        }

        habit.setUpdatedAt(Instant.now());
        Habit updated = habitRepository.save(habit);

        Integer streak = calculateCurrentStreak(id);
        Long total = dailyProgressRepository.countCompletedByHabitId(id);

        return HabitDTO.from(updated, streak, total);
    }

    // DELETE (soft delete)
    @Transactional
    public void delete(String id) {
        Habit habit = habitRepository
            .findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException("Habit not found with id: " + id)
            );

        habit.setDeleted(true);
        habit.setUpdatedAt(Instant.now());
        habitRepository.save(habit);
    }

    // UPDATE PROGRESS
    @Transactional
    public void updateProgress(String habitId, ProgressUpdateDTO dto) {
        Habit habit = habitRepository
            .findById(habitId)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "Habit not found with id: " + habitId
                )
            );

        DailyProgress progress = dailyProgressRepository
            .findByHabitIdAndDate(habitId, dto.date())
            .orElse(new DailyProgress());

        progress.setHabitId(habit.getId());
        progress.setUserId(habit.getUserId());
        progress.setDate(dto.date());
        progress.setCompleted(dto.completed());
        progress.setNotes(dto.notes());

        if (progress.getId() == null) {
            progress.setCreatedAt(Instant.now());
        }
        progress.setUpdatedAt(Instant.now());

        dailyProgressRepository.save(progress);
    }

    // CALCULATE CURRENT STREAK
    public Integer calculateCurrentStreak(String habitId) {
        LocalDate today = LocalDate.now();
        LocalDate checkDate = today;
        int streak = 0;

        // Verifica se completou hoje
        boolean completedToday = dailyProgressRepository
            .findByHabitIdAndDate(habitId, today)
            .map(DailyProgress::getCompleted)
            .orElse(false);

        if (!completedToday) {
            // Se não completou hoje, começa a verificar de ontem
            checkDate = today.minusDays(1);
        }

        // Conta dias consecutivos para trás
        while (true) {
            boolean completed = dailyProgressRepository
                .findByHabitIdAndDate(habitId, checkDate)
                .map(DailyProgress::getCompleted)
                .orElse(false);

            if (!completed) {
                break;
            }

            streak++;
            checkDate = checkDate.minusDays(1);

            // Limita a busca para evitar loop infinito (ex: últimos 365 dias)
            if (streak > 365) {
                break;
            }
        }

        return streak;
    }

    // GET PROGRESS FOR DATE RANGE
    public List<DailyProgress> getProgressForDateRange(
        String habitId,
        LocalDate startDate,
        LocalDate endDate
    ) {
        if (!habitRepository.existsById(habitId)) {
            throw new ResourceNotFoundException(
                "Habit not found with id: " + habitId
            );
        }

        return dailyProgressRepository.findByHabitIdAndDateBetween(
            habitId,
            startDate,
            endDate
        );
    }
}
