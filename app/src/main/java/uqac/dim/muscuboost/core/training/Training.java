package uqac.dim.muscuboost.core.training;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.core.schedule.ISlottable;

/**
 * A training that contains exercises.
 * It can be slotted in a {@link uqac.dim.muscuboost.core.schedule.ScheduleSlot}.
 */
public class Training implements ISlottable, Serializable {

    private final long ID;
    private String name;
    private List<Exercise> exercises = new ArrayList<>();

    /**
     * Creates a training.
     *
     * @param id Id of the training
     * @param name Name of the training
     */
    public Training(long id, String name) {
        this(id, name, null);
    }

    /**
     * Creates a training.
     *
     * @param id Id of the training
     * @param name Name of the training
     * @param exercises Exercises of the training
     */
    public Training(long id, String name, List<Exercise> exercises) {
        ID = id;
        this.name = name;
        if(exercises != null)
            this.exercises = exercises;
    }

    /**
     * Returns the training's id.
     *
     * @return Id of the training
     */
    public long getId() {
        return ID;
    }

    /**
     * Defines the training's name.
     *
     * @param name Name of the training
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the training's name.
     *
     * @return Name of the training
     */
    public String getName() {
        return name;
    }

    /**
     * Adds an exercise to the training.
     *
     * @param exercise Exercise to add to the training
     */
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
    }

    /**
     * Removes an exercise from the training.
     *
     * @param exercise Exercise to remove from the training
     */
    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
    }

    /**
     * Returns the training's exercise list.
     *
     * @return Exercise list of the training
     */
    public List<Exercise> getExercises() {
        return exercises;
    }

    /**
     * Returns the schedule slotted training's label.
     *
     * @return Label of the schedule slotted training
     */
    @Override
    public String getSlotLabel() {
        return name;
    }

    /**
     * Returns whether the given object equals the training.
     *
     * @param obj Object to compare
     * @return True if the given object equals the training, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if(obj == null)
            return false;
        if(obj.getClass() != getClass())
            return false;
        Training other = (Training) obj;
        return other.ID == ID
          && other.name.equals(name)
          && other.exercises.equals(exercises);
    }

}
