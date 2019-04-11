package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.core.training.Muscle;

public class ExerciseDAO extends DAOSingleKey<Exercise> {

    private MuscleDAO muscleDao = new MuscleDAO(context);

    public static final String TABLE_NAME = "exercise";

    public static final String KEY = "id";
    public static final String NAME = "name";
    public static final String MUSCLE_ID = "muscle_id";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME + " VARCHAR(30) NOT NULL,"
                    + MUSCLE_ID + " INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + MUSCLE_ID + ") REFERENCES " + MuscleDAO.TABLE_NAME + "(" + MuscleDAO.KEY + ") );";

    public ExerciseDAO(Context context) {
        super(context, TABLE_NAME, KEY);
    }

    @Override
    public void open() {
        super.open();
        muscleDao.open();
    }

    @Override
    public void close() {
        super.close();
        muscleDao.close();
    }

    public Exercise insert(String name, Muscle muscle) {
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        values.put(MUSCLE_ID, muscle.getId());
        long id = db.insert(MuscleDAO.TABLE_NAME, null, values);
        return new Exercise(id, name, muscle);
    }

    @Override
    public void update(Exercise exercise) {
        ContentValues values = new ContentValues();
        values.put(NAME, exercise.getName());
        values.put(MUSCLE_ID, exercise.getMuscle().getId());
        String[] whereArgs = {String.valueOf(exercise.getId())};
        db.update(TABLE_NAME, values, KEY + " = ?", whereArgs);
    }

    @Override
    public List<Exercise> getAll(String whereSQL, String[] whereArgs) {
        Cursor c = getGetAllCursor(whereSQL, whereArgs);
        List<Exercise> exercices = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(KEY));
            String name = c.getString(c.getColumnIndex(NAME));
            long muscleId = c.getLong(c.getColumnIndex(MUSCLE_ID));
            Muscle muscle = muscleDao.get(muscleId);
            exercices.add(new Exercise(id, name, muscle));
        }
        return exercices;
    }

}
