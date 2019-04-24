package uqac.dim.muscuboost.core.person;

import java.util.Date;

public class Body {

    private double masse;
    private double taille;
    private Date date_enregistrement;

    public Body(double masse, double taille, Date date_enregistrement){
        this.masse = masse;
        this.taille = taille;
        this.date_enregistrement = date_enregistrement;
    }

    public double getMasse() {
        return masse;
    }

    public double getTaille() {
        return taille;
    }

    public Date getDate_enregistrement() {
        return date_enregistrement;
    }

    public double calculIMC(){
        double taille_metre = taille/100;
        double imc = masse/Math.pow(taille_metre,2);
        return imc;
    }
}
