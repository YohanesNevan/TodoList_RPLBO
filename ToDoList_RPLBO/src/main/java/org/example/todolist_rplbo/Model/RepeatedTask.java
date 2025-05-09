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


    public RepeatedTask(int id, String name, String description, LocalDateTime dueDate,
                        String priority, String category, String status, String repeatType, int userId, LocalDateTime startDate, LocalDateTime endDate, DayOfWeek targetDayOfWeek) {
        super(); // atribut turunan
        setName(name);
        setDescription(description);
        setDueDate(dueDate);
        setPriority(priority);
        setCategory(category);
        setStatus(status);
        this.id = id;
        this.userId = userId;
        this.repeatType = RepeatType.valueOf(repeatType.toUpperCase());
        this.startDate = startDate;
        this.endDate = endDate;

        if (this.repeatType == RepeatType.SPECIFIC_DAY) {
            this.targetDayOfWeek = targetDayOfWeek;
        }
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
                if (targetDayOfWeek != null) {
                    int currentDayValue = currentDate.getDayOfWeek().getValue(); // hari ini hari apa
                    int targetValue = targetDayOfWeek.getValue(); // mau diulang setiap hari apa
                    int daysUntilTarget = (targetValue - currentDayValue + 7) % 7; // berapa hari ke target perulangan
                    return currentDate.plusDays(daysUntilTarget == 0 ? 7 : daysUntilTarget); // kalau hari ini, ke minggu depan
                } else {
                    return currentDate;
                }
            default:
                return currentDate;
        }
    }
}
