package uqac.dim.muscuboost.core.schedule;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ScheduleTest {

    private Schedule schedule;
    private ScheduleSlot slot;

    @Before
    public void setUp() {
        schedule = new Schedule();
        slot = new ScheduleSlot(0, Day.MONDAY, new ISlottable() {
            @Override
            public String getSlotLabel() {
                return null;
            }
        });
    }

    @Test
    public void getIdTest() {
        assertEquals(0, slot.getId());
    }

    @Test
    public void addSlotTest() {
        assertEquals(0, schedule.getSlotsByDay(slot.getDay()).size());
        schedule.addSlot(slot);
        assertEquals(1, schedule.getSlotsByDay(slot.getDay()).size());
    }

    @Test
    public void removeSlotTest() {
        schedule.addSlot(slot);
        assertEquals(1, schedule.getSlotsByDay(slot.getDay()).size());
        schedule.removeSlot(slot);
        assertEquals(0, schedule.getSlotsByDay(slot.getDay()).size());
    }

    @Test
    public void getSlotsTest() {
        assertEquals(Day.getWeek().size(), schedule.getSlots().size());
        assertEquals(0, schedule.getSlots().get(slot.getDay()).size());
        schedule.addSlot(slot);
        assertEquals(1, schedule.getSlots().get(slot.getDay()).size());
        assertEquals(slot, schedule.getSlots().get(slot.getDay()).get(0));
    }

    @Test
    public void getSlotsByDay() {
        schedule.addSlot(slot);
        assertEquals(1, schedule.getSlotsByDay(Day.MONDAY).size());
        assertEquals(slot, schedule.getSlotsByDay(Day.MONDAY).get(0));
        assertEquals(0, schedule.getSlotsByDay(Day.TUESDAY).size());
    }

}
