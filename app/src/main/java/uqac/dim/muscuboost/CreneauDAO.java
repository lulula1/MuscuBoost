package uqac.dim.muscuboost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class CreneauDAO extends DAOBase{

    public static final String TABLE_NAME = "creneau";

    public static final String KEY = "id";
    public static final String JOUR = "jour";
    public static final String HEURE_DEBUT = "heureDebut";
    public static final String HEURE_FIN = "heureFin";
    public static final String ID_ENTRAINEMENT = "id_entrainement";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + JOUR + " VARCHAR(15),"
                    + HEURE_DEBUT + " VARCHAR(6),"
                    + HEURE_FIN + " VARCHAR(6),"
                    + ID_ENTRAINEMENT + " INTEGER,"
                    + "FOREIGN KEY (" + ID_ENTRAINEMENT + ") REFERENCES " + EntrainementDAO.TABLE_NAME + "(" + EntrainementDAO.KEY + ") ON DELETE CASCADE );";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public CreneauDAO(Context pContext) {
        super(pContext);
    }

    public void ajouter(Creneau c){
        ContentValues value = new ContentValues();
        value.put(JOUR, c.getJour());
        value.put(HEURE_DEBUT, c.getHeureDebut());
        value.put(HEURE_FIN, c.getHeureFin());
        value.put(ID_ENTRAINEMENT, c.getEntrainement().getId());
        bdd.insert(MuscleDAO.TABLE_NAME, null, value);
    }

    public void supprimer(Creneau c){
        supprimer(c.getId());
    }

    public void supprimer(int id){
        String[] whereArgs = { String.valueOf(id) };
        bdd.delete(TABLE_NAME, KEY + " = ?", whereArgs);
    }

    public void modifier(Creneau c){
        ContentValues value = new ContentValues();
        value.put(JOUR, c.getJour());
        value.put(HEURE_DEBUT, c.getHeureDebut());
        value.put(HEURE_FIN, c.getHeureFin());
        value.put(ID_ENTRAINEMENT, c.getEntrainement().getId());
        String[] whereArgs = { String.valueOf(c.getId()) };
        bdd.update(TABLE_NAME, value, KEY  + " = ?",whereArgs);
    }

    public ArrayList<Creneau> selectionner(String whereSQL, String[] whereArgs){
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_NAME + whereSQL, whereArgs);
        ArrayList<Creneau> array = new ArrayList<Creneau>();
        while(c.moveToNext()){
            String[] strings = { c.getString(4) };
            Cursor c1 = bdd.rawQuery("SELECT * FROM "+ EntrainementDAO.TABLE_NAME+" WHERE id = ?",  strings);
            c1.moveToNext();
            /*strings = {  };
            Entrainement e = new Entrainement(c1.getString(1), c1.getInt(2), ExerciceDAO.selectionner("WHERE id = ?"));
            Creneau c = new Exercice(m, c.getString(1));
            e.setId(c.getInt(0));
            c1.close();
            array.add(e);*/
        }
        return array;
    }

}
