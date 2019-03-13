package uqac.dim.muscuboost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class ExerciceDAO extends DAOBase {

    public static final String TABLE_NAME = "exercice";

    public static final String KEY = "id";
    public static final String INTITULE = "intitule";
    public static final String MUSCLE = "muscle";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + INTITULE + " VARCHAR(30),"
                    + MUSCLE + " INTEGER,"
                    + "FOREIGN KEY (" + MUSCLE + ") REFERENCES " + MuscleDAO.TABLE_NAME + "(" + MuscleDAO.KEY + ") ON DELETE CASCADE);";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public ExerciceDAO(Context pContext) {
        super(pContext);
    }

    public void ajouter(Exercice e){
        ContentValues value = new ContentValues();
        value.put(INTITULE, e.getIntitule());
        value.put(MUSCLE, e.getMuscle().getId());
        bdd.insert(MuscleDAO.TABLE_NAME, null, value);
    }

    public void supprimer(Exercice e){
        String[] whereArgs = { e.getIntitule() };
        bdd.delete(TABLE_NAME, INTITULE + " = ?", whereArgs);
    }

    public void supprimer(int id){
        String[] whereArgs = { String.valueOf(id) };
        bdd.delete(TABLE_NAME, KEY + " = ?", whereArgs);
    }

    public void modifier(Exercice e){
        ContentValues value = new ContentValues();
        value.put(INTITULE, e.getIntitule());
        value.put(MUSCLE, e.getMuscle().getId());
        String[] whereArgs = { String.valueOf(e.getId()) };
        bdd.update(TABLE_NAME, value, KEY  + " = ?",whereArgs);
    }

    public static ArrayList<Exercice> selectionner(String whereSQL, String[] whereArgs){
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_NAME + whereSQL, whereArgs);
        ArrayList<Exercice> array = new ArrayList<Exercice>();
        while(c.moveToNext() && c.getCount() != 0){
            String[] strings = { c.getString(2) };
            Cursor c1 = bdd.rawQuery("SELECT * FROM muscle WHERE id = ?",  strings);
            c1.moveToNext();
            Muscle m = new Muscle(c1.getInt(0), c1.getString(1));
            Exercice e = new Exercice(m, c.getString(1));
            e.setId(c.getInt(0));
            c1.close();
            array.add(e);
        }
        return array;
    }
}
