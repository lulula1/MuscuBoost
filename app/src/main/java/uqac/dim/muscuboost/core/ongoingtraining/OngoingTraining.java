package uqac.dim.muscuboost.core.ongoingtraining;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.core.training.Training;

/**
 * A training currently being practiced.
 */
public class OngoingTraining {

    private Training training;
    private Exercise currentExercise;
    private int series;
    private List<Exercise> doneExercises = new ArrayList<>();

    /**
     * Creates an ongoing training.
     *
     * @param training Training that is going to be practiced
     */
    public OngoingTraining(Training training) {
        Objects.requireNonNull(training, "Training must not be null");
        this.training = training;
        // TODO - Don't oblige the currentExercise to be the first training exercise
        if(!training.getExercises().isEmpty())
            currentExercise = training.getExercises().get(0);
        series = 1;
    }

    /**
     * Returns the currently practiced exercise.
     *
     * @return Currently practiced exercise
     */
    public Exercise getCurrentExercise() {
        return currentExercise;
    }

    /**
     * Returns the current exercise series count.
     *
     * @return Series count of the current exercise
     */
    public int getSeries() {
        return series;
    }

    /**
     * Returns the ongoing training.
     *
     * @return Ongoing training
     */
    public Training getTraining() {
        return training;
    }

    /**
     * Returns whether the ongoing training is over.
     *
     * @return True if the training has ended, false otherwise
     */
    public boolean isTrainingOver() {
        return getCurrentExercise() == null;
    }

    /**
     * Returns the count of exercises of the training.
     *
     * @return Count of exercises
     */
    public int getExerciseCount() {
        return training.getExercises().size();
    }

    /**
     * Returns the count of exercises done during the training.
     *
     * @return Count of done exercises
     */
    public int getDoneExercisesCount() {
        return doneExercises.size();
    }

    /**
     * Proceed to the next exercise. This will reset the series count.
     */
    public void nextExercise() {
        if(!isTrainingOver()) {
            doneExercises.add(currentExercise);
            if(doneExercises.size() < training.getExercises().size()) {
                // TODO - Don't oblige the currentExercise to be the next training exercise
                currentExercise = training.getExercises().get(doneExercises.size());
                series = 1;
            }else {
                currentExercise = null;
            }
        }
    }

    /**
     * Proceed to the next series count.
     */
    public void nextSeries() {
        if(!isTrainingOver())
            series++;
    }

}
