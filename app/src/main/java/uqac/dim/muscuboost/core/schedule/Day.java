package uqac.dim.muscuboost.core.schedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a day of week.
 */
public enum Day {
    MONDAY    (0, "monday"),
    TUESDAY   (1, "tuesday"),
    WEDNESDAY (2, "wednesday"),
    THURSDAY  (3, "thursday"),
    FRIDAY    (4, "friday"),
    SATURDAY  (5, "saturday"),
    SUNDAY    (6, "sunday");

    private final long ID;
    private final String NAME;

    /**
     * Creates a day.
     *
     * @param id Numerical day of the week format
     * @param name Name of the day
     */
    Day(long id, String name) {
        ID = id;
        NAME = name;
    }

    /**
     * Numerical day of the week format.
     * From 0 for monday to 6 for sunday.
     *
     * @return Id of the day
     */
    public long getId() {
        return ID;
    }

    /**
     * Returns the days of a week.
     * From monday to sunday.
     *
     * @return Days of the week
     */
    public static List<Day> getWeek() {
        return new ArrayList<>(
                Arrays.asList(MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY));
    }

    @Override
    public String toString() {
        return NAME;
    }

}
