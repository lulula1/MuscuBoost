package uqac.dim.muscuboost.core.training;

public class ScheduleSlot {

    private int id;
    private String day;
    private int hour;
    private int minute;
    private Training training;

    public ScheduleSlot(String day, int hour, int minute, Training training) {
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.training = training;
    }

    public int getId() {
        return id;
    }

    public String getDay() {
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
