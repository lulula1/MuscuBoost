package uqac.dim.muscuboost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

public class EntrainementDAO extends DAOBase {

    public static final String TABLE_NAME = "entrainement";

    public static final String KEY = "id";
    public static final String INTITULE = "intitule";
    public static final String NIVEAU = "niveau";
    public static final String ID_EXERCICES = "id_exercices";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + INTITULE + " VARCHAR(30),"
                    + NIVEAU + "INTEGER );";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public EntrainementDAO(Context pContext) {
        super(pContext);
    }

    public void ajouter(Entrainement e){
        ContentValues value = new ContentValues();
        value.put(INTITULE, e.getIntitule());
        value.put(NIVEAU, e.getNiveau());
        bdd.insert(EntrainementDAO.TABLE_NAME, null, value);

        e.setId(getLastId());

        for(Exercice exo : e.getExercices()){
            value.clear();
            value.put("id_entrainement", e.getId());
            value.put("id_exercice", exo.getId());
            bdd.insert(DAOBase.LIGNE_ENT, null, value);
        }
    }

    public void supprimer(Entrainement e){
        String[] whereArgs = { e.getIntitule() };
        bdd.delete(TABLE_NAME, INTITULE + " = ?", whereArgs);
    }

    public void supprimer(int id){
        String[] whereArgs = { String.valueOf(id) };
        bdd.delete(TABLE_NAME, KEY + " = ?", whereArgs);
    }

    public void modifier(Entrainement e){
        ContentValues value = new ContentValues();
        value.put(INTITULE, e.getIntitule());
        value.put(NIVEAU, e.getNiveau());
        String[] whereArgs = { String.valueOf(e.getId()) };
        bdd.update(TABLE_NAME, value, KEY  + " = ?",whereArgs);

        bdd.delete(DAOBase.LIGNE_ENT, "id_entrainement = ?", whereArgs);
        for(Exercice exo : e.getExercices()){
            value.clear();
            value.put("id_entrainement", e.getId());
            value.put("id_exercice", exo.getId());
            bdd.insert(DAOBase.LIGNE_ENT, null, value);
        }
    }

    public Cursor selectionner(String query, String[] whereArgs){
        Cursor c = bdd.rawQuery(query, whereArgs);
        return c;
    }

    private int getLastId(){
        Cursor c = selectionner("SELECT MAX(id) FROM "+ TABLE_NAME, null);
        c.moveToNext();
        return c.getInt(0);
    }
}


