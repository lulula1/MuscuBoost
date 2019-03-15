package uqac.dim.muscuboost.core.schedule;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DayTest {

    @Test
    public void getIdTest() {
        assertEquals(0, Day.MONDAY.getId());
        assertEquals(1, Day.TUESDAY.getId());
        assertEquals(2, Day.WEDNESDAY.getId());
        assertEquals(3, Day.THURSDAY.getId());
        assertEquals(4, Day.FRIDAY.getId());
        assertEquals(5, Day.SATURDAY.getId());
        assertEquals(6, Day.SUNDAY.getId());
    }

    @Test
    public void getWeekTest() {
        List<Day> week = Day.getWeek();
        assertEquals(7, week.size());
        assertEquals(Day.MONDAY, week.get(0));
        assertEquals(Day.TUESDAY, week.get(1));
        assertEquals(Day.WEDNESDAY, week.get(2));
        assertEquals(Day.THURSDAY, week.get(3));
        assertEquals(Day.FRIDAY, week.get(4));
        assertEquals(Day.SATURDAY, week.get(5));
        assertEquals(Day.SUNDAY, week.get(6));
    }

    @Test
    public void getDayByIdTest() {
        assertNull(Day.getDayById(-1));
        assertEquals(Day.MONDAY, Day.getDayById(0));
        assertEquals(Day.SUNDAY, Day.getDayById(6));
        assertNull(Day.getDayById(7));
    }

    @Test
    public void toStringTest() {
        assertEquals("monday", Day.MONDAY.toString());
        assertEquals("tuesday", Day.TUESDAY.toString());
        assertEquals("wednesday", Day.WEDNESDAY.toString());
        assertEquals("thursday", Day.THURSDAY.toString());
        assertEquals("friday", Day.FRIDAY.toString());
        assertEquals("saturday", Day.SATURDAY.toString());
        assertEquals("sunday", Day.SUNDAY.toString());
    }

}
