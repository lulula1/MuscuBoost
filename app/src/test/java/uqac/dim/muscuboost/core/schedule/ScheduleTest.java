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
        slot = new ScheduleSlot(Day.MONDAY, new Slottable() {
            @Override
            public String getSlotLabel() {
                return null;
            }
        });
    }

    @Test
    public void getSlotTest() {
        assertEquals(0, schedule.getSlots().size());
        schedule.addSlot(slot);
        assertEquals(1, schedule.getSlots().size());
        assertEquals(slot, schedule.getSlots().get(0));
    }

    @Test
    public void addSlotTest() {
        assertEquals(0, schedule.getSlots().size());
        schedule.addSlot(slot);
        assertEquals(1, schedule.getSlots().size());
    }

    @Test
    public void removeSlotTest() {
        schedule.addSlot(slot);
        assertEquals(1, schedule.getSlots().size());
        schedule.removeSlot(slot);
        assertEquals(0, schedule.getSlots().size());
    }

    @Test
    public void getSlotsByDay() {
        schedule.addSlot(slot);
        assertEquals(1, schedule.getSlotsByDay(Day.MONDAY).size());
        assertEquals(slot, schedule.getSlotsByDay(Day.MONDAY).get(0));
        assertEquals(0, schedule.getSlotsByDay(Day.TUESDAY).size());
    }

}
