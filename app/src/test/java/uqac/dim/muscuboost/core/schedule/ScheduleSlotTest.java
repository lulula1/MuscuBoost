package uqac.dim.muscuboost.core.schedule;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ScheduleSlotTest {

    private ISlottable slottable;
    private ScheduleSlot slotDay;
    private ScheduleSlot slotDayTime;

    @Before
    public void setUp() {
        slottable = new ISlottable() {
            @Override
            public String getSlotLabel() {
                return null;
            }
        };
        slotDay = new ScheduleSlot(0, Day.MONDAY, slottable);
        slotDayTime = new ScheduleSlot(10, Day.WEDNESDAY, 15, 30, slottable);
    }

    @Test
    public void getIdTest() {
        assertEquals(0, slotDay.getId());
        assertEquals(10, slotDayTime.getId());
    }

    @Test
    public void getDayTest() {
        assertEquals(Day.MONDAY, slotDay.getDay());
        assertEquals(Day.WEDNESDAY, slotDayTime.getDay());
    }

    @Test
    public void setDayTest() {
        assertEquals(Day.MONDAY, slotDay.getDay());
        slotDay.setDay(Day.SUNDAY);
        assertEquals(Day.SUNDAY, slotDay.getDay());
    }

    @Test
    public void getHourTest() {
        assertNull(slotDay.getHour());
        assertEquals(15, (int) slotDayTime.getHour());
    }

    @Test
    public void setHourTest() {
        assertNull(slotDay.getHour());
        slotDay.setHour(0);
        assertEquals(0, (int) slotDay.getHour());

        assertEquals(15, (int) slotDayTime.getHour());
        slotDayTime.setHour(24);
        assertEquals(24, (int) slotDayTime.getHour());
    }

    @Test(expected=IllegalArgumentException.class)
    public void setHourLowerTest() {
        slotDay.setHour(-1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void setHourGreaterTest() {
        slotDay.setHour(25);
    }

    @Test
    public void getMinuteTest() {
        assertNull(slotDay.getMinute());
        assertEquals(30, (int) slotDayTime.getMinute());
    }

    @Test
    public void setMinuteTest() {
        assertNull(slotDay.getMinute());
        slotDay.setMinute(0);
        assertEquals(0, (int) slotDay.getMinute());

        assertEquals(30, (int) slotDayTime.getMinute());
        slotDayTime.setMinute(60);
        assertEquals(60, (int) slotDayTime.getMinute());
    }

    @Test(expected=IllegalArgumentException.class)
    public void setMinuteLowerTest() {
        slotDay.setMinute(-1);
    }

    @Test(expected=IllegalArgumentException.class)
    public void setMinuteGreaterTest() {
        slotDay.setMinute(61);
    }

    @Test
    public void getFormattedTimeTest() {
        assertEquals("--:--", slotDay.getFormattedTime());
        assertEquals("15:30", slotDayTime.getFormattedTime());

        slotDay.setHour(9);
        slotDay.setMinute(9);
        assertEquals("09:09", slotDay.getFormattedTime());
    }

    @Test
    public void getItemTest() {
        assertEquals(slottable, slotDay.getItem());
        assertEquals(slottable, slotDayTime.getItem());
    }

    @Test
    public void setItemTest() {
        ISlottable sc = new ISlottable() {
            @Override
            public String getSlotLabel() {
                return null;
            }
        };

        slotDay.setItem(sc);
        assertEquals(sc, slotDay.getItem());
    }

}
