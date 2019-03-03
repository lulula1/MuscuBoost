package uqac.dim.muscuboost.core.training;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExerciseTest {

    private Exercise exercise1;
    private Exercise exercise2;

    @Before
    public void setUp() {
        exercise1 = new Exercise(0, "Exercise1", Muscle.BICEPS);
        exercise2 = new Exercise(10, "Exercise2", Muscle.TRICEPS);
    }

    @Test
    public void getIdTest() {
        assertEquals(0, exercise1.getId());
        assertEquals(10, exercise2.getId());
    }

    @Test
    public void setNameTest() {
        assertEquals("Exercise1", exercise1.getName());
        exercise1.setName("ExerciseRenamed");
        assertEquals("ExerciseRenamed", exercise1.getName());
    }

    @Test
    public void getNameTest() {
        assertEquals("Exercise1", exercise1.getName());
        assertEquals("Exercise2", exercise2.getName());
    }

    @Test
    public void setMuscle() {
        assertEquals(Muscle.BICEPS, exercise1.getMuscle());
        exercise1.setMuscle(Muscle.PECTORAL);
        assertEquals(Muscle.PECTORAL, exercise1.getMuscle());
    }

    @Test
    public void getMuscle() {
        assertEquals(Muscle.BICEPS, exercise1.getMuscle());
        assertEquals(Muscle.TRICEPS, exercise2.getMuscle());
    }

}