package uqac.dim.muscuboost.core.ongoingtraining;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.core.training.Training;

import static org.junit.Assert.*;

public class OngoingTrainingTest {

    private Exercise exercise1;
    private Exercise exercise2;
    private Training training;
    private OngoingTraining ongoingTraining;
    private OngoingTraining ongoingTrainingEmpty;

    @Before
    public void setUp() {
        exercise1 = new Exercise(0, "Exercise", null);
        exercise2 = new Exercise(1, "Exercise2", null);
        training = new Training(0, "Training1", Arrays.asList(exercise1, exercise2));
        ongoingTraining = new OngoingTraining(training);
        ongoingTrainingEmpty = new OngoingTraining(new Training(1, "Training2"));
    }

    @Test(expected=NullPointerException.class)
    public void setNullTrainingTest() {
        new OngoingTraining(null);
    }

    @Test
    public void getCurrentExerciseTest() {
        assertEquals(exercise1, ongoingTraining.getCurrentExercise());
        assertNull(ongoingTrainingEmpty.getCurrentExercise());
    }

    @Test
    public void getSeriesTest() {
        assertEquals(1, ongoingTraining.getSeries());
        assertEquals(1, ongoingTrainingEmpty.getSeries());
    }

    @Test
    public void getTrainingTest() {
        assertEquals(training, ongoingTraining.getTraining());
    }

    @Test
    public void isTrainingOverTest() {
        assertFalse(ongoingTraining.isTrainingOver());
        ongoingTraining.nextExercise();
        ongoingTraining.nextExercise();
        assertTrue(ongoingTraining.isTrainingOver());

        assertTrue(ongoingTrainingEmpty.isTrainingOver());
    }

    @Test
    public void nextExerciseTest() {

        assertFalse(ongoingTraining.isTrainingOver());
        assertEquals(exercise1, ongoingTraining.getCurrentExercise());
        ongoingTraining.nextExercise();
        assertEquals(exercise2, ongoingTraining.getCurrentExercise());
        ongoingTraining.nextExercise();
        assertNull(ongoingTraining.getCurrentExercise());

        assertNull(ongoingTrainingEmpty.getCurrentExercise());
    }

    @Test
    public void nextSeriesTest() {
        assertEquals(1, ongoingTraining.getSeries());
        ongoingTraining.nextSeries();
        assertEquals(2, ongoingTraining.getSeries());

        assertEquals(1, ongoingTrainingEmpty.getSeries());
        ongoingTrainingEmpty.nextSeries();
        assertEquals(1, ongoingTrainingEmpty.getSeries());
    }

}
