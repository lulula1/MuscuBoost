package uqac.dim.muscuboost.core.training;

public class Creneau {

    private int id;
    private String jour;
    private int heure;
    private int minute;
    private Entrainement entrainement;

    public Creneau(String jour, int heure, int minute, Entrainement entrainement) {
        this.jour = jour;
        this.heure = heure;
        this.minute = minute;
        this.entrainement = entrainement;
    }

    public int getId() {
        return id;
    }

    public String getJour() {
        return jour;
    }

    public int getHeure() {
        return heure;
    }

    public int getMinute() {
        return minute;
    }

    public Entrainement getEntrainement() {
        return entrainement;
    }
}
