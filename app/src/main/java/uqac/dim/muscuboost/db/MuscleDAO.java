package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.core.training.Muscle;

public class MuscleDAO extends DAOBase {
    public static final String TABLE_NAME = "muscle";

    public static final String KEY = "id";
    public static final String NAME = "name";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME + " VARCHAR(30) NOT NULL );";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public MuscleDAO(Context pContext) {
        super(pContext);
    }

    public void insert(Muscle muscle) {
        ContentValues value = new ContentValues();
        value.put(MuscleDAO.NAME, muscle.getName());
        db.insert(MuscleDAO.TABLE_NAME, null, value);
    }

    public void delete(Muscle muscle) {
        String[] whereArgs = {muscle.getName()};
        db.delete(TABLE_NAME, NAME + " = ?", whereArgs);
    }

    public void delete(long id) {
        String[] whereArgs = {String.valueOf(id)};
        db.delete(TABLE_NAME, KEY + " = ?", whereArgs);
    }

    public void update(Muscle muscle) {
        ContentValues value = new ContentValues();
        value.put(NAME, muscle.getName());
        String[] whereArgs = {String.valueOf(muscle.getId())};
        db.update(TABLE_NAME, value, KEY + " = ?", whereArgs);
    }

    public List<Muscle> select(String whereSQL, String[] whereArgs) {
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + whereSQL, whereArgs);
        List<Muscle> array = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(0);
            String name = c.getString(1);
            Muscle m = new Muscle(id, name);
            array.add(m);
        }
        return array;
    }

}
