package uqac.dim.muscuboost.core.ongoingtraining;

/**
 * Represents an object that can observe a subject.
 *
 * @see Subject
 */
public interface Observer {

    /**
     * Called when the subject is altered.
     */
    void onUpdate();

}
