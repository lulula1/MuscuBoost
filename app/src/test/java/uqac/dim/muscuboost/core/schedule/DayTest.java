package uqac.dim.muscuboost.core.schedule;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class DayTest {

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

}
