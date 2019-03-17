package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.core.training.Training;

public class TrainingDAO extends DAOBase {

    public static final String TABLE_NAME = "training";

    public static final String KEY = "id";
    public static final String NAME = "name";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + NAME + " VARCHAR(30) NOT NULL );";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public TrainingDAO(Context pContext) {
        super(pContext);
    }

    public Training insert(String name) {
        ContentValues value = new ContentValues();
        value.put(NAME, name);
        long id = db.insert(TrainingDAO.TABLE_NAME, null, value);
        return new Training(id, name);
    }

    public void delete(Training training) {
        String[] whereArgs = {String.valueOf(training.getId())};
        db.delete(TABLE_NAME, KEY + " = ?", whereArgs);
    }

    public void update(Training training) {
        ContentValues value = new ContentValues();
        value.put(NAME, training.getName());
        String[] whereArgs = {String.valueOf(training.getId())};
        db.update(TABLE_NAME, value, KEY + " = ?", whereArgs);

        db.delete(TrainingExerciseDAO.TABLE_NAME,
                TrainingExerciseDAO.TRAINING_ID + " = ?", whereArgs);
        for (Exercise exo : training.getExercises()) {
            value.clear();
            value.put(TrainingExerciseDAO.TRAINING_ID, training.getId());
            value.put(TrainingExerciseDAO.EXERCISE_ID, exo.getId());
            db.insert(TrainingExerciseDAO.TABLE_NAME, null, value);
        }
    }

    public Cursor select(String query, String[] whereArgs) {
        Cursor c = db.rawQuery(query, whereArgs);
        return c;
    }

    private int getLastId() {
        Cursor c = select("SELECT MAX(" + KEY + ") FROM " + TABLE_NAME, null);
        c.moveToNext();
        return c.getInt(0);
    }
}


