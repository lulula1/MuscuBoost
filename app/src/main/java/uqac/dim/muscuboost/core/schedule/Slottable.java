package uqac.dim.muscuboost.core.schedule;

/**
 * An element that can be included in a ScheduleSlot.
 */
public interface Slottable {

    /**
     * The label of the slotted element.
     *
     * @return Label of the element
     */
    String getSlotLabel();

}
