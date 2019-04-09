package uqac.dim.muscuboost.core.training;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MuscleTest {

    private Muscle muscle;

    @Before
    public void setUp() {
        muscle = new Muscle(0, "Biceps");
    }


    @Test
    public void getIdTest() {
        assertEquals(0, muscle.getId());
    }

    @Test
    public void setNameTest() {
        assertEquals("Biceps", muscle.getName());
        muscle.setName("Triceps");
        assertEquals("Triceps", muscle.getName());
    }

    @Test
    public void getNameTest() {
        assertEquals("Biceps", muscle.getName());
    }

    @Test
    public void equalsTest() {
        assertEquals(muscle, new Muscle(0,"Biceps"));
        assertNotEquals(muscle, null);
        assertNotEquals(muscle, new Object());
        assertNotEquals(muscle, new Muscle(1,"Biceps"));
        assertNotEquals(muscle, new Muscle(0,"Triceps"));
    }

}
