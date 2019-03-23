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

    public MuscleDAO(Context context) {
        super(context);
    }

    public Muscle addMuscle(String name) {
        ContentValues values = new ContentValues();
        values.put(MuscleDAO.NAME, name);
        long id = db.insert(MuscleDAO.TABLE_NAME, null, values);
        return new Muscle(id, name);
    }

    public void removeMuscle(Muscle muscle) {
        String[] whereArgs = {String.valueOf(muscle.getId())};
        db.delete(TABLE_NAME, KEY + " = ?", whereArgs);
    }

    public void updateMuscle(Muscle muscle) {
        ContentValues values = new ContentValues();
        values.put(NAME, muscle.getName());
        String[] whereArgs = {String.valueOf(muscle.getId())};
        db.update(TABLE_NAME, values, KEY + " = ?", whereArgs);
    }

    public List<Muscle> getMuscles() {
        return getMuscles(null, null);
    }

    public List<Muscle> getMuscles(String whereSQL, String[] whereArgs) {
        Cursor c = db.rawQuery("SELECT * "
                        + "FROM " + TABLE_NAME
                        + (whereSQL != null ? " WHERE " + whereSQL : ""),
                whereArgs);
        List<Muscle> muscles = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(KEY));
            String name = c.getString(c.getColumnIndex(NAME));
            muscles.add(new Muscle(id, name));
        }
        return muscles;
    }

    public Muscle getMuscle(long muscleId) {
        String[] whereArgs = {String.valueOf(muscleId)};
        List<Muscle> muscles = getMuscles(KEY + " = ?", whereArgs);
        return !muscles.isEmpty() ? muscles.get(0) : null;
    }

}
