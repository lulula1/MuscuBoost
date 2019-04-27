package uqac.dim.muscuboost.core.training;

import java.io.Serializable;

import uqac.dim.muscuboost.core.Identifiable;

/**
 * A training exercise that is associated to a muscle.
 */
public class Exercise implements Identifiable, Serializable {

    private final long ID;
    private String name;
    private Muscle muscle;
    private String description;

    /**
     * Creates an exercise.
     *
     * @param id Id of the exercise
     * @param name Name of the exercise
     * @param muscle Muscle involved in the exercise
     */
    public Exercise(long id, String name, Muscle muscle,  String description) {
        ID = id;
        this.name = name;
        this.muscle = muscle;
        this.description = description;
    }

    /**
     * Returns the exercise's id.
     *
     * @return Id of the exercise
     */
    @Override
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
     * Returns the description involved in the exercise.
     *
     * @return Description of the exercise
     */
    public Muscle getMuscle() {
        return muscle;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    /**
     * Returns the description involved in the exercise.
     *
     * @return description of the exercise
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns whether the given object equals the exercise.
     *
     * @param obj Object to compare
     * @return True if the given object equals the exercise, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj.getClass() != getClass())
            return false;
        Exercise other = (Exercise) obj;
        if(other.muscle == null && muscle == null)
            return true;
        return other.ID == ID
                && other.name.equals(name)
                && other.muscle.equals(muscle);
    }

    @Override
    public String toString() {
        return  name;
    }

}
