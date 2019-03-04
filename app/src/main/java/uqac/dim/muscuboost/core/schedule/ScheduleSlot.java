package uqac.dim.muscuboost.core.schedule;

/**
 * A temporal slot of a schedule.
 */
public class ScheduleSlot {

    private Day day;
    private Integer hour;
    private Integer minute;
    private ISlottable item;

    /**
     * A schedule temporal slot.
     *
     * @param day  Day of week of the slot
     * @param hour Hour of the slot
     * @param minute Minute of the slot
     * @param item Item contained in the slot
     */
    public ScheduleSlot(Day day, Integer hour, Integer minute, ISlottable item) {
        setDay(day);
        setHour(hour);
        setMinute(minute);
        setItem(item);
    }

    /**
     * A schedule temporal slot without a specific time.
     *
     * @param day  Day of week of the slot
     * @param item Item contained in the slot
     */
    public ScheduleSlot(Day day, ISlottable item) {
        this(day, null, null, item);
    }

    /**
     * Defines the slot's day of week.
     *
     * @param day New day of week of the slot
     */
    public void setDay(Day day) {
        this.day = day;
    }

    /**
     * Returns the slot's day of week.
     *
     * @return Day of week of the slot
     */
    public Day getDay() {
        return day;
    }

    /**
     * Defines the slot's hour.
     *
     * @param hour New hour of the slot
     */
    public void setHour(Integer hour) throws IllegalArgumentException {
        if(hour != null && (hour < 0 || hour > 24))
            throw new IllegalArgumentException("Hour must be between 0 and 24");
        this.hour = hour;
    }

    /**
     * Returns the slot's hour.
     *
     * @return Hour of the slot
     */
    public Integer getHour() {
        return hour;
    }

    /**
     * Defines the minute's slot.
     *
     * @param minute New minute of the slot
     */
    public void setMinute(Integer minute) throws IllegalArgumentException {
        if(minute != null && (minute < 0 || minute > 60))
            throw new IllegalArgumentException("Minute must be between 0 and 60");
        this.minute = minute;
    }

    /**
     * Returns the minute's slot.
     *
     * @return Minute of the slot
     */
    public Integer getMinute() {
        return minute;
    }

    /**
     * Returns the time (hour and minute) in the 'hh:mm' formatted way
     *
     * @return Formatted hour and minute time
     */
    public String getFormattedTime() {
        return (hour != null ? (hour <= 9 ? "0" : "") + hour : "--")
                + ":" +  (minute != null ? (minute <= 9 ? "0" : "") + minute : "--");
    }

    /**
     * Defines the item contained in the slot.
     *
     * @param item New item contained in the slot
     */
    public void setItem(ISlottable item) {
        this.item = item;
    }

    /**
     * Returns the item contained in the slot.
     *
     * @return Item contained in the slot
     */
    public ISlottable getItem() {
        return item;
    }

    /**
     * Returns the string label value attached to the slot.
     *
     * @return The slot label
     */
    public String getLabel() {
        return item.getSlotLabel();
    }

}
