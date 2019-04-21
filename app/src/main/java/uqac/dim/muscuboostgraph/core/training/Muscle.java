package uqac.dim.muscuboostgraph.core.training;

public class Muscle {

    private long id;
    private String name;

    public Muscle(String name) {
        this.name = name;
    }

    public Muscle(long id, String name) {
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

    public long getId() {
        return id;
    }
}
