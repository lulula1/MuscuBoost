package uqac.dim.muscuboost.core.training;

public class Exercise {

    private int id;
    private Muscle muscle;
    private String name;

    public Exercise(Muscle muscle, String name) {
        this.muscle = muscle;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public Muscle getMuscle() {
        return muscle;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }
}
