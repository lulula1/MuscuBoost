package uqac.dim.muscuboost.core.training;

/**
 * A training exercise that is associated to a muscle.
 */
public class Exercise {

    private final long ID;
    private String name;
    private Muscle muscle;

    /**
     * Creates an exercise.
     *
     * @param id Id of the exercise
     * @param name Name of the exercise
     * @param muscle Muscle involved in the exercise
     */
    public Exercise(long id, String name, Muscle muscle) {
        ID = id;
        this.name = name;
        this.muscle = muscle;
    }

    /**
     * Returns the exercise's id.
     *
     * @return Id of the exercise
     */
    public long getId() {
        return ID;
    }

    /**
     * Defines the exercise's name.
     *
     * @param name New name of the exercise
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the exercise's name.
     *
     * @return Name of the exercise
     */
    public String getName() {
        return name;
    }

    /**
     * Defines the muscle involved in the exercise.
     *
     * @param muscle New muscle of the exercise
     */
    public void setMuscle(Muscle muscle) {
        this.muscle = muscle;
    }

    /**
     * Returns the muscle involved in the exercise.
     *
     * @return Muscle of the exercise
     */
    public Muscle getMuscle() {
        return muscle;
    }
}
