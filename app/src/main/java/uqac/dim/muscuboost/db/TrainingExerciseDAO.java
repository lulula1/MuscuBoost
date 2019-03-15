package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;

import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.core.training.Training;

public class TrainingExerciseDAO extends DAOBase {

    public static final String TABLE_NAME = "training_exercice";

    public static final String TRAINING_ID = "training_id";
    public static final String EXERCISE_ID = "exercise_id";

    protected static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + TRAINING_ID + " INTEGER NOT NULL,"
                    + EXERCISE_ID + " INTEGER NOT NULL,"
                    + "PRIMARY KEY (" + TRAINING_ID + ", " + EXERCISE_ID + "),"
                    + "FOREIGN KEY (" + TRAINING_ID + ") REFERENCES " + TrainingDAO.TABLE_NAME + "(" + TrainingDAO.KEY + ") ON DELETE CASCADE,"
                    + "FOREIGN KEY (" + EXERCISE_ID + ") REFERENCES " + ExerciseDAO.TABLE_NAME + "(" + ExerciseDAO.KEY + ") ON DELETE CASCADE );";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public TrainingExerciseDAO(Context pContext) {
        super(pContext);
    }

    public void insert(Training training, Exercise exercise) {
        ContentValues value = new ContentValues();
        value.put(TrainingExerciseDAO.TRAINING_ID, training.getId());
        value.put(TrainingExerciseDAO.EXERCISE_ID, exercise.getId());
        db.insert(TrainingExerciseDAO.TABLE_NAME, null, value);
    }

    public void delete(Training training, Exercise exercise) {
        String[] whereArgs = {String.valueOf(training.getId()),
                String.valueOf(exercise.getId())};
        db.delete(TrainingExerciseDAO.TABLE_NAME,
                TrainingExerciseDAO.TRAINING_ID + " = ? AND"
                        + TrainingExerciseDAO.EXERCISE_ID + " = ?", whereArgs);
    }

}
