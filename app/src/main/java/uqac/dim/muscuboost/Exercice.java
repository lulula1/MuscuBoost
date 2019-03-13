package uqac.dim.muscuboost;

public class Exercice {

    private int id;
    private Muscle muscle;
    private String intitule;

    public Exercice(Muscle muscle, String intitule){
        this.muscle = muscle;
        this.intitule = intitule;
    }

    public int getId() {
        return id;
    }

    public Muscle getMuscle() {
        return muscle;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setId(int id) {
        this.id = id;
    }
}
