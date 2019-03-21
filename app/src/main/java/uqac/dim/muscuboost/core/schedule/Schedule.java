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
     * Removes a schedule slot.
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
        List<ScheduleSlot> daySlots = new ArrayList<>();
        for(ScheduleSlot slot : slots)
            if(slot.getDay() == day)
                daySlots.add(getSortIndex(slot, daySlots), slot);
        return daySlots;
    }

    /**
     * Returns the appropriate index on which the slot may be inserted
     * to keep the slotList ordered by time.
     *
     * @param slot Slot to be inserted
     * @param slotList Sample of slots to compare the slot to to get the appropriate slot index
     * @return Appropriate slot index
     */
    private static int getSortIndex(ScheduleSlot slot, List<ScheduleSlot> slotList) {
        int index = 0;
        for(ScheduleSlot otherSlot : slotList)
            if(slot.getDay() == otherSlot.getDay()
                    && slot.getHour() * 60 + slot.getMinute()
                    >= otherSlot.getHour() * 60 + otherSlot.getMinute())
                index++;
        return index;
    }

}
