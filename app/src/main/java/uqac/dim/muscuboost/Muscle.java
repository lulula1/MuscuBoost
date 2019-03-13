package uqac.dim.muscuboost;

public class Muscle {

    private int id;
    private String intitule;

    public Muscle(String nom){
        this.intitule = nom;
    }

    public Muscle(int id, String nom){
        this.intitule = nom;
    }

    public void setIntitule(String nom){
        this.intitule = nom;
    }

    public String getIntitule(){
        return intitule;
    }

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }
}
