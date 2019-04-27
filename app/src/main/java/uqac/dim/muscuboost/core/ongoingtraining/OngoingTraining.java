package uqac.dim.muscuboost.core.ongoingtraining;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.core.training.Statistics;
import uqac.dim.muscuboost.core.training.Training;

/**
 * A training currently being practiced.
 */
public class OngoingTraining {

    private Training training;
    private Exercise currentExercise;
    private Statistics currentExerciseStats;
    private Map<Exercise, Statistics> doneExerciseStats = new LinkedHashMap<>();

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
        currentExerciseStats = new Statistics();
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
     * Defines the current exercise lifted weight.
     *
     * @param weight New lifted weight of the current exercise
     */
    public void setWeight(double weight) {
        currentExerciseStats.setWeight(weight);
    }

    /**
     * Returns the current exercise lifted weight.
     *
     * @return Lifted weight of the current exercise
     */
    public double getWeight() {
        return currentExerciseStats.getWeight();
    }

    /**
     * Returns the current exercise series count.
     *
     * @return Series count of the current exercise
     */
    public int getSeries() {
        return currentExerciseStats.getRepCount();
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
        return currentExercise == null;
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
        return doneExerciseStats.size();
    }

    /**
     * Returns the done exercises associated to their statistics.
     *
     * @return Done exercise associated to their statistics
     */
    public Map<Exercise, Statistics> getDoneExerciseStats() {
        return doneExerciseStats;
    }

    /**
     * Proceed to the next exercise. This will reset the series count.
     */
    public void nextExercise() {
        if(!isTrainingOver()) {
            doneExerciseStats.put(currentExercise, currentExerciseStats);
            if(doneExerciseStats.size() < training.getExercises().size()) {
                // TODO - Don't oblige the currentExercise to be the next training exercise
                currentExercise = training.getExercises().get(doneExerciseStats.size());
                currentExerciseStats = new Statistics();
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
            currentExerciseStats.setRepCount(
                    currentExerciseStats.getRepCount() + 1);
    }

}
