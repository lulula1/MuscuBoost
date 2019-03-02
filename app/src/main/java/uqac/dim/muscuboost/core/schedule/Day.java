package uqac.dim.muscuboost.core.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a day of week.
 */
public enum Day {
    MONDAY    ("monday"),
    TUESDAY   ("tuesday"),
    WEDNESDAY ("wednesday"),
    THURSDAY  ("thursday"),
    FRIDAY    ("friday"),
    SATURDAY  ("saturday"),
    SUNDAY    ("sunday");

    private final String NAME;

    Day(String name) {
        NAME = name;
    }

    @Override
    public String toString() {
        return NAME;
    }

    public static List<Day> getWeek() {
        return new ArrayList<>(
                Arrays.asList(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY));
    }

}
