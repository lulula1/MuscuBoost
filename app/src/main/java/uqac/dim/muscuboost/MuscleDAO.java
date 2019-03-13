package uqac.dim.muscuboost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

public class MuscleDAO extends DAOBase {
    public static final String TABLE_NAME = "muscle";

    public static final String KEY = "id";
    public static final String INTITULE = "intitule";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + INTITULE + " VARCHAR(30) );";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public MuscleDAO(Context pContext) {
        super(pContext);
    }

    public void ajouter(Muscle m){
        ContentValues value = new ContentValues();
        value.put(MuscleDAO.INTITULE, m.getIntitule());
        bdd.insert(MuscleDAO.TABLE_NAME, null, value);
    }

    public void supprimer(Muscle m){
        String[] whereArgs = { m.getIntitule() };
        bdd.delete(TABLE_NAME, INTITULE + " = ?", whereArgs);
    }

    public void supprimer(int id){
        String[] whereArgs = { String.valueOf(id) };
        bdd.delete(TABLE_NAME, KEY + " = ?", whereArgs);
    }

    public void modifier(Muscle m){
        ContentValues value = new ContentValues();
        value.put(INTITULE, m.getIntitule());
        String[] whereArgs = { String.valueOf(m.getId()) };
        bdd.update(TABLE_NAME, value, KEY  + " = ?",whereArgs);
    }

    public ArrayList<Muscle> selectionner(String whereSQL, String[] whereArgs){
        Cursor c = bdd.rawQuery("SELECT * FROM " + TABLE_NAME + whereSQL, whereArgs);
        ArrayList<Muscle> array = new ArrayList<Muscle>();
        while(c.moveToNext()){
            Muscle m = new Muscle(c.getString(1));
            m.setId(c.getInt(0));
            array.add(m);
        }
        return array;
    }

}
