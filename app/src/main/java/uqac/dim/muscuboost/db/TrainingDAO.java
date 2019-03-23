package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.core.training.Training;

public class TrainingDAO extends DAOBase {

    private TrainingExerciseDAO trainingExerciseDao = new TrainingExerciseDAO(context);

    public static final String TABLE_NAME = "training";

    public static final String KEY = "id";
    public static final String NAME = "name";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + NAME + " VARCHAR(30) NOT NULL );";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public TrainingDAO(Context context) {
        super(context);
    }

    @Override
    public SQLiteDatabase open() {
        trainingExerciseDao.open();
        return super.open();
    }

    @Override
    public void close() {
        super.close();
        trainingExerciseDao.close();
    }

    public Training addTraining(String name) {
        ContentValues values = new ContentValues();
        values.put(NAME, name);
        long id = db.insert(TrainingDAO.TABLE_NAME, null, values);
        return new Training(id, name);
    }

    public void removeTraining(Training training) {
        String[] whereArgs = {String.valueOf(training.getId())};
        db.delete(TABLE_NAME, KEY + " = ?", whereArgs);
    }

    public void updateTraining(Training training) {
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

    public List<Training> getTrainings() {
        return getTrainings(null, null);
    }

    public List<Training> getTrainings(String whereSQL, String[] whereArgs) {
        Cursor c = db.rawQuery("SELECT * "
                        + "FROM " + TABLE_NAME
                        + (whereSQL != null ? " WHERE " + whereSQL : ""),
                whereArgs);
        List<Training> trainings = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(KEY));
            String name = c.getString(c.getColumnIndex(NAME));
            List<Exercise> exercises = trainingExerciseDao.getExercises(id);
            trainings.add(new Training(id, name, exercises));
        }
        return trainings;
    }

    public Training getTraining(long trainingId) {
        String[] whereArgs = {String.valueOf(trainingId)};
        List<Training> trainings = getTrainings(KEY + " = ?", whereArgs);
        return !trainings.isEmpty() ? trainings.get(0) : null;
    }

}


