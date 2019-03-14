package uqac.dim.muscuboost.core.training;

public class Muscle {

    private int id;
    private String name;

    public Muscle(String name) {
        this.name = name;
    }

    public Muscle(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
