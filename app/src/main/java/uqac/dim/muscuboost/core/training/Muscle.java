package uqac.dim.muscuboost.core.training;

/**
 * A body muscle.
 */
public class Muscle {

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

}
