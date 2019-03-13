package uqac.dim.muscuboost;

import java.util.ArrayList;

public class Entrainement {

    private int id;
    private String intitule;
    private int niveau;
    private ArrayList<Exercice> exercices;

    public Entrainement(String intitule, int niveau, ArrayList<Exercice> exercices){
        this.intitule = intitule;
        this.niveau = niveau;
        this.exercices = exercices;
    }

    public int getId() {
        return id;
    }

    public String getIntitule() {
        return intitule;
    }

    public int getNiveau() {
        return niveau;
    }

    public ArrayList<Exercice> getExercices() {
        return exercices;
    }

    public void setId(int id) {
        this.id = id;
    }
}
