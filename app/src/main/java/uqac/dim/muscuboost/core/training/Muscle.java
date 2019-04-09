package uqac.dim.muscuboost.core.training;

import java.io.Serializable;

/**
 * A body muscle.
 */
public class Muscle implements Serializable {

    private final long ID;
    private String name;

    /**
     * Creates a muscle.
     *
     * @param id Id of the muscle
     * @param name Name of the muscle
     */
    public Muscle(long id, String name) {
        ID = id;
        this.name = name;
    }

    /**
     * Returns the muscle's id.
     *
     * @return Id of the muscle
     */
    public long getId() {
        return ID;
    }

    /**
     * Defines the muscle's name.
     *
     * @param name New name of the muscle
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the muscle's name.
     *
     * @return Name of the muscle
     */
    public String getName() {
        return name;
    }

    /**
     * Returns whether the given object equals the muscle.
     *
     * @param obj Object to compare
     * @return True if the given object equals the muscle, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj.getClass() != getClass())
            return false;
        Muscle other = (Muscle) obj;
        return other.ID == ID
                && other.name.equals(name);
    }

}
