package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.core.training.Exercise;

public class TrainingExerciseDAO extends DAOBase {

    private ExerciseDAO exerciseDao = new ExerciseDAO(context);

    public static final String TABLE_NAME = "training_exercise";

    public static final String TRAINING_ID = "training_id";
    public static final String EXERCISE_ID = "exercise_id";

    protected static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + TRAINING_ID + " INTEGER NOT NULL,"
                    + EXERCISE_ID + " INTEGER NOT NULL,"
                    + "PRIMARY KEY (" + TRAINING_ID + ", " + EXERCISE_ID + "),"
                    + "FOREIGN KEY (" + TRAINING_ID + ") REFERENCES " + TrainingDAO.TABLE_NAME + "(" + TrainingDAO.KEY + ") ON DELETE CASCADE,"
                    + "FOREIGN KEY (" + EXERCISE_ID + ") REFERENCES " + ExerciseDAO.TABLE_NAME + "(" + ExerciseDAO.KEY + ") ON DELETE CASCADE );";

    public TrainingExerciseDAO(Context context) {
        super(context);
    }

    @Override
    public void open() {
        super.open();
        exerciseDao.open();
    }

    @Override
    public void close() {
        super.close();
        exerciseDao.close();
    }

    public void insert(long trainingId, long exerciseId) {
        ContentValues values = new ContentValues();
        values.put(TRAINING_ID, trainingId);
        values.put(EXERCISE_ID, exerciseId);
        db.insert(TABLE_NAME, null, values);
    }

    public void delete(long trainingId, long exerciseId) {
        String[] whereArgs = {String.valueOf(trainingId),
                String.valueOf(exerciseId)};
        db.delete(TrainingExerciseDAO.TABLE_NAME,
                TrainingExerciseDAO.TRAINING_ID + " = ? AND "
                        + TrainingExerciseDAO.EXERCISE_ID + " = ?", whereArgs);
    }

    public List<Exercise> getAllExercises(long trainingId) {
        String[] whereArgs = {String.valueOf(trainingId)};
        Cursor c = db.rawQuery("SELECT *"
                        + "FROM " + TABLE_NAME + " "
                        + "WHERE " + TRAINING_ID + " = ?",
                whereArgs);
        List<Exercise> exercises = new ArrayList<>();
        while (c.moveToNext()) {
            long exerciseId = c.getLong(c.getColumnIndex(EXERCISE_ID));
            exercises.add(exerciseDao.get(exerciseId));
        }
        return exercises;
    }

}
