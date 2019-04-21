package uqac.dim.muscuboostgraph.core.training;

import java.util.List;

public class Training {

    private long id;
    private String name;
    private List<Exercise> exercises;

    public Training(String name, List<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    public Training(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setId(int id) {
        this.id = id;
    }
}
