package uqac.dim.muscuboost;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Creneau {

    private int id;
    private String jour;
    private String heureDebut;
    private String heureFin;
    private Entrainement entrainement;

    public Creneau(String jour, String heureDebut, String heurFin, Entrainement entrainement){
        this.jour = jour;
        this.heureDebut = heureDebut;
        this.heureFin = heurFin;
        this.entrainement = entrainement;
    }

    public int getId() {
        return id;
    }

    public String getJour() {
        return jour;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public Entrainement getEntrainement() {
        return entrainement;
    }
}
