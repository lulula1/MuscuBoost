package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.core.training.Training;

public class TrainingDAO extends DAOSingleKey<Training> {

    private TrainingExerciseDAO trainingExerciseDao = new TrainingExerciseDAO(context);

    public static final String TABLE_NAME = "training";

    public static final String KEY = "id";
    public static final String NAME = "name";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + NAME + " VARCHAR(30) NOT NULL );";

    public TrainingDAO(Context context) {
        super(context, TABLE_NAME, KEY);
    }

    @Override
    public void open() {
        super.open();
        trainingExerciseDao.open();
    }

    @Override
    public void close() {
        super.close();
        trainingExerciseDao.close();
    }

    public Training insert(String name) {
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        long id = db.insert(TrainingDAO.TABLE_NAME, null, values);
        return new Training(id, name);
    }

    @Override
    public void update(Training training) {
        ContentValues values = new ContentValues();
        values.put(NAME, training.getName());
        String[] whereArgs = {String.valueOf(training.getId())};
        db.update(TABLE_NAME, values, KEY + " = ?", whereArgs);

        db.delete(TrainingExerciseDAO.TABLE_NAME,
                TrainingExerciseDAO.TRAINING_ID + " = ?", whereArgs);
        for (Exercise exo : training.getExercises()) {
            values.clear();
            values.put(TrainingExerciseDAO.TRAINING_ID, training.getId());
            values.put(TrainingExerciseDAO.EXERCISE_ID, exo.getId());
            db.insert(TrainingExerciseDAO.TABLE_NAME, null, values);
        }
    }

    @Override
    public List<Training> getAll(String whereSQL, String[] whereArgs) {
        Cursor c = getGetAllCursor(whereSQL, whereArgs);
        List<Training> trainings = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(KEY));
            String name = c.getString(c.getColumnIndex(NAME));
            List<Exercise> exercises = trainingExerciseDao.getAllExercises(id);
            trainings.add(new Training(id, name, exercises));
        }
        return trainings;
    }

}


