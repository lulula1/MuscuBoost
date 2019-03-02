package uqac.dim.muscuboost.core.schedule;

/**
 * An element that can be included in a ScheduleSlot.
 */
public interface Slottable {

    /**
     * Returns the slotted element's label.
     *
     * @return Label of the element
     */
    String getSlotLabel();

}
