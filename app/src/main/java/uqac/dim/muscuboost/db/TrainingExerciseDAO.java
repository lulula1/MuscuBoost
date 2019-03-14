package uqac.dim.muscuboost.db;

import android.content.Context;

public class TrainingExerciseDAO extends DAOBase {

    public static final String TABLE_NAME = "ligne_entrainement";

    public static final String TRAINING_ID = "training_id";
    public static final String EXERCISE_ID = "exercise_id";

    protected static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + TRAINING_ID + " INTEGER,"
                    + EXERCISE_ID + " INTEGER,"
                    + "PRIMARY KEY (" + TRAINING_ID + ", " + EXERCISE_ID + "),"
                    + "FOREIGN KEY (" + TRAINING_ID + ") REFERENCES " + TrainingDAO.TABLE_NAME + "(" + TrainingDAO.KEY + ") ON DELETE CASCADE,"
                    + "FOREIGN KEY (" + EXERCISE_ID + ") REFERENCES " + ExerciseDAO.TABLE_NAME + "(" + ExerciseDAO.KEY + ") ON DELETE CASCADE );";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public TrainingExerciseDAO(Context pContext) {
        super(pContext);
    }

}
