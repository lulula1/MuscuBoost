package uqac.dim.muscuboost.core.training;

import java.util.List;

public class Training {

    private int id;
    private String name;
    private List<Exercise> exercises;

    public Training(String name, List<Exercise> exercises) {
        this.name = name;
        this.exercises = exercises;
    }

    public int getId() {
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
