package uqac.dim.muscuboostgraph.core.training;

public class Statistics {

    private long id;
    private double poids;
    private int nb_rep;

    public Statistics(long id, double poids, int nb_rep){
        this.id = id;
        this.poids = poids;
        this.nb_rep = nb_rep;
    }

    public Statistics(double poids, int nb_rep){
        this.poids = poids;
        this.nb_rep = nb_rep;
    }

    public long getId(){
        return id;
    }

    public double getPoids() {
        return poids;
    }

    public int getNb_rep() {
        return nb_rep;
    }

    public void setId(long id) {
        this.id = id;
    }
}
