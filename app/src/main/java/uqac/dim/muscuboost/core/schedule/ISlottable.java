package uqac.dim.muscuboost.core.schedule;

import java.io.Serializable;

/**
 * An element that can be included in a ScheduleSlot.
 */
public interface ISlottable extends Serializable {

    /**
     * Returns the slotted element's label.
     *
     * @return Label of the element
     */
    String getSlotLabel();

}
