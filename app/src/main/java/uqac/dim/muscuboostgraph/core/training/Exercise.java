package uqac.dim.muscuboostgraph.core.training;

public class Exercise {

    private long id;
    private Muscle muscle;
    private String name;

    public Exercise(long id, String name, Muscle muscle) {
        this.id = id;
        this.muscle = muscle;
        this.name = name;
    }

    public long getId() {
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
