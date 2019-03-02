package uqac.dim.muscuboost.core.training;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.core.schedule.Slottable;

/**
 * A training that contains exercises.
 * It can be slotted in a {@link uqac.dim.muscuboost.core.schedule.ScheduleSlot}.
 */
public class Training implements Slottable {

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
        ID = id;
        this.name = name;
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

}
