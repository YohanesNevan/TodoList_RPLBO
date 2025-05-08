package org.example.todolist_rplbo.Model;

import java.time.LocalDateTime;
import java.time.DayOfWeek;

public class RepeatedTask extends Task {
    private int id;
    private int userId;
    private RepeatType repeatType; // // "Daily", "Weekly", "Monthly", "Specific Day"
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private DayOfWeek targetDayOfWeek;

    public enum RepeatType {
        DAILY,
        WEEKLY,
        MONTHLY,
        SPECIFIC_DAY
    }


    public RepeatedTask(int id, int userId, String name, String description, LocalDateTime dueDate,
                        String priority, String category, RepeatType repeatType, LocalDateTime startDate, LocalDateTime endDate) {
        super(name, description, dueDate, priority, category); // atribut turunan
        this.id = id;
        this.userId = userId;
        this.repeatType = repeatType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public LocalDateTime getNextOccurrence(LocalDateTime currentDate) { // cari kapan tasknya muncul lagi
        switch (repeatType) {
            case DAILY:
                return currentDate.plusDays(1);
            case WEEKLY:
                return currentDate.plusWeeks(1);
            case MONTHLY:
                return currentDate.plusMonths(1);
            case SPECIFIC_DAY:
                int currentDayValue = currentDate.getDayOfWeek().getValue(); // hari ini hari apa
                int targetValue = targetDayOfWeek.getValue(); // mau diulang setiap hari apa
                int daysUntilTarget = (targetValue - currentDayValue + 7) % 7; // berapa hari ke target perulangan
                return currentDate.plusDays(daysUntilTarget == 0 ? 7 : daysUntilTarget); // kalau hari ini, ke minggu depan
            default:
                return currentDate;
        }
    }
}
