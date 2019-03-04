package uqac.dim.muscuboost.core.schedule;

import java.util.ArrayList;
import java.util.List;

/**
 * A schedule with daily (and hourly) temporized slots.
 */
public class Schedule {

    private List<ScheduleSlot> slots = new ArrayList<>();

    /**
     * Adds a new schedule slot.
     *
     * @param slot Schedule slot to be added
     */
    public void addSlot(ScheduleSlot slot) {
        slots.add(slot);
    }

    /**
     * Removes a scedule slot.
     *
     * @param slot Schedule slot to be removed
     */
    public void removeSlot(ScheduleSlot slot) {
        slots.remove(slot);
    }

    /**
     * Returns schedule slots.
     *
     * @return Schedule slots
     */
    public List<ScheduleSlot> getSlots() {
        return slots;
    }

    /**
     * Returns all the slots matching the given day.
     *
     * @param day The day on which to filter slots
     * @return Slots matching the given day
     */
    public List<ScheduleSlot> getSlotsByDay(Day day) {
        List<ScheduleSlot> slotsByDay = new ArrayList<>();
        for(ScheduleSlot slot : slots)
            if(slot.getDay() == day)
                slotsByDay.add(slot);
        return slotsByDay;
    }

}
