package uqac.dim.muscuboost.core.training;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TrainingTest {

    private Exercise exercise;
    private Training training1;
    private Training training2;

    @Before
    public void setUp() {
        exercise = new Exercise(0, "Exercise", Muscle.BICEPS);
        training1 = new Training(0, "Training1");
        training2 = new Training(10, "Training2");
    }

    @Test
    public void getIdTest() {
        assertEquals(0, training1.getId());
        assertEquals(10, training2.getId());
    }

    @Test
    public void setNameTest() {
        assertEquals("Training1", training1.getName());
        training1.setName("TrainingRenamed");
        assertEquals("TrainingRenamed", training1.getName());
    }

    @Test
    public void getNameTest() {
        assertEquals("Training1", training1.getName());
        assertEquals("Training2", training2.getName());
    }

    @Test
    public void addExerciseTest() {
        assertEquals(0, training1.getExercises().size());
        training1.addExercise(exercise);
        assertEquals(1, training1.getExercises().size());
    }

    @Test
    public void removeExerciseTest() {
        training1.addExercise(exercise);
        assertEquals(1, training1.getExercises().size());
        training1.removeExercise(exercise);
        assertEquals(0, training1.getExercises().size());
    }

    @Test
    public void getExercisesTest() {
        training1.addExercise(exercise);
        assertEquals(1, training1.getExercises().size());
        assertEquals(exercise, training1.getExercises().get(0));
    }

    @Test
    public void getSlotLabelTest() {
        assertEquals("Training1", training1.getSlotLabel());
        assertEquals("Training2", training2.getSlotLabel());
    }

}
