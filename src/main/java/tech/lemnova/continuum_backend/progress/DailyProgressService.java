package tech.lemnova.continuum_backend.progress;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.lemnova.continuum_backend.exception.ResourceNotFoundException;
import tech.lemnova.continuum_backend.habit.Habit;
import tech.lemnova.continuum_backend.habit.HabitRepository;

@Service
public class DailyProgressService {

    private final DailyProgressRepository dailyProgressRepository;
    private final HabitRepository habitRepository;

    public DailyProgressService(
        DailyProgressRepository dailyProgressRepository,
        HabitRepository habitRepository
    ) {
        this.dailyProgressRepository = dailyProgressRepository;
        this.habitRepository = habitRepository;
    }

    // GET PROGRESS FOR HABIT
    public List<DailyProgressDTO> getProgressForHabit(
        Long habitId,
        LocalDate startDate,
        LocalDate endDate
    ) {
        if (!habitRepository.existsById(habitId)) {
            throw new ResourceNotFoundException("Habit", habitId);
        }

        return dailyProgressRepository
            .findByHabitIdAndDateBetween(habitId, startDate, endDate)
            .stream()
            .map(DailyProgressDTO::from)
            .collect(Collectors.toList());
    }

    // GET PROGRESS FOR USER (para heatmap geral)
    public List<DailyProgressDTO> getProgressForUser(
        Long userId,
        LocalDate startDate,
        LocalDate endDate
    ) {
        return dailyProgressRepository
            .findByUserIdAndDateBetween(userId, startDate, endDate)
            .stream()
            .map(DailyProgressDTO::from)
            .collect(Collectors.toList());
    }

    // GET TODAY'S PROGRESS FOR USER
    public List<DailyProgressDTO> getTodayProgressForUser(Long userId) {
        LocalDate today = LocalDate.now();
        return dailyProgressRepository
            .findByUserIdAndDate(userId, today)
            .stream()
            .map(DailyProgressDTO::from)
            .collect(Collectors.toList());
    }

    // TOGGLE PROGRESS (marcar/desmarcar)
    @Transactional
    public DailyProgressDTO toggleProgress(Long habitId, LocalDate date) {
        Habit habit = habitRepository
            .findById(habitId)
            .orElseThrow(() -> new ResourceNotFoundException("Habit", habitId));

        DailyProgress progress = dailyProgressRepository
            .findByHabitIdAndDate(habitId, date)
            .orElse(new DailyProgress());

        if (progress.getId() == null) {
            // Criar novo registro
            progress.setHabit(habit);
            progress.setDate(date);
            progress.setCompleted(true);
        } else {
            // Toggle
            progress.setCompleted(!progress.getCompleted());
        }

        DailyProgress saved = dailyProgressRepository.save(progress);
        return DailyProgressDTO.from(saved);
    }

    // CALCULATE DAILY SCORE FOR USER
    public Integer calculateDailyScore(Long userId, LocalDate date) {
        List<DailyProgress> progressList =
            dailyProgressRepository.findByUserIdAndDate(userId, date);

        long completed = progressList
            .stream()
            .filter(DailyProgress::getCompleted)
            .count();

        long total = progressList.size();

        if (total == 0) {
            return 0;
        }

        // Score de 0-100 baseado na porcentagem de hábitos completados
        return (int) ((completed * 100) / total);
    }
}
