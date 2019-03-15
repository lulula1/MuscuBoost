package uqac.dim.muscuboost.core.schedule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A schedule with daily (and hourly) temporized slots.
 */
public class Schedule {

    private Map<Day, List<ScheduleSlot>> slots = new HashMap<>();

    /**
     * A schedule.
     */
    public Schedule() {
        for(Day day : Day.getWeek())
            slots.put(day, new ArrayList<ScheduleSlot>());
    }

    /**
     * Adds a new schedule slot.
     *
     * @param slot Schedule slot to be added
     */
    public void addSlot(ScheduleSlot slot) {
        getSlotsByDay(slot.getDay())
                .add(getAddIndex(slot), slot);
    }

    /**
     * Returns the appropriate index on which to insert the slot
     * to keep the schedule ordered by time.
     *
     * @param slot Slot to be inserted
     * @return Appropriate slot index
     */
    private int getAddIndex(ScheduleSlot slot) {
        int i = 0;
        List<ScheduleSlot> slots = getSlotsByDay(slot.getDay());
        while(slots.size() > i
                && slot.getHour() * 60 + slot.getMinute()
                    >= slots.get(i).getHour() * 60 + slots.get(i).getMinute())
            i++;
        return i;
    }

    /**
     * Removes a schedule slot.
     *
     * @param slot Schedule slot to be removed
     */
    public void removeSlot(ScheduleSlot slot) {
        slots.get(slot.getDay()).remove(slot);
    }

    /**
     * Returns schedule slots.
     *
     * @return Schedule slots
     */
    public Map<Day, List<ScheduleSlot>> getSlots() {
        return slots;
    }

    /**
     * Returns all the slots matching the given day.
     *
     * @param day The day on which to filter slots
     * @return Slots matching the given day
     */
    public List<ScheduleSlot> getSlotsByDay(Day day) {
        return slots.get(day);
    }

}
