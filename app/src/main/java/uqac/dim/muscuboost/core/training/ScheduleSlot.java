package uqac.dim.muscuboost.core.training;

public class ScheduleSlot {

    private long id;
    private Day day;
    private int hour;
    private int minute;
    private Training training;

    public ScheduleSlot(long id, Day day, int hour, int minute, Training training) {
        this.id = id;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.training = training;
    }

    public long getId() {
        return id;
    }

    public Day getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public Training getTraining() {
        return training;
    }
}
