package uqac.dim.muscuboost.core.ongoingtraining;

/**
 * Represents a subjects which can be observed by observers.
 *
 * @see Observer
 */
public interface Subject {

    /**
     * Registers an Observe in the observers list.
     * Registered observers will be notified when notifyUpdate is called.
     *
     * @param observer Observer to be registered
     */
    void register(Observer observer);

    /**
     * Unregisters an Observer from the observers list.
     *
     * @param observer Observer to be unregistered
     */
    void unregister(Observer observer);

    /**
     * Notifies the registered observers an updated occurred
     */
    void notifyUpdate();

}
