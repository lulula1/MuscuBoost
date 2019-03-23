package uqac.dim.muscuboost.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

    // Database version
    protected static final int VERSION = 10;

    // Database (file) name
    protected static final String DATABASE_NAME = "muscuboost.db";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MuscleDAO.TABLE_CREATE);
        db.execSQL(ExerciseDAO.TABLE_CREATE);
        db.execSQL(TrainingDAO.TABLE_CREATE);
        db.execSQL(TrainingExerciseDAO.TABLE_CREATE);
        db.execSQL(SlotDAO.TABLE_CREATE);

        // Insert default muscles
        db.execSQL("INSERT INTO " + MuscleDAO.TABLE_NAME + "(" + MuscleDAO.NAME + ") VALUES ('Deltoide')");
        db.execSQL("INSERT INTO " + MuscleDAO.TABLE_NAME + "(" + MuscleDAO.NAME + ") VALUES ('Biceps')");
        db.execSQL("INSERT INTO " + MuscleDAO.TABLE_NAME + "(" + MuscleDAO.NAME + ") VALUES ('Triceps')");
        db.execSQL("INSERT INTO " + MuscleDAO.TABLE_NAME + "(" + MuscleDAO.NAME + ") VALUES ('Pectauraux')");

        // Insert default exercises
        db.execSQL("INSERT INTO " + ExerciseDAO.TABLE_NAME + "(" + ExerciseDAO.NAME + "," + ExerciseDAO.MUSCLE_ID + ") VALUES ('Developpé-Couché', 4)");
        db.execSQL("INSERT INTO " + ExerciseDAO.TABLE_NAME + "(" + ExerciseDAO.NAME + "," + ExerciseDAO.MUSCLE_ID + ") VALUES ('Haltere', 2)");
        db.execSQL("INSERT INTO " + ExerciseDAO.TABLE_NAME + "(" + ExerciseDAO.NAME + "," + ExerciseDAO.MUSCLE_ID + ") VALUES ('Tractions', 3)");
        db.execSQL("INSERT INTO " + ExerciseDAO.TABLE_NAME + "(" + ExerciseDAO.NAME + "," + ExerciseDAO.MUSCLE_ID + ") VALUES ('Deeps', 1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MuscleDAO.TABLE_DROP);
        db.execSQL(ExerciseDAO.TABLE_DROP);
        db.execSQL(TrainingDAO.TABLE_DROP);
        db.execSQL(TrainingExerciseDAO.TABLE_DROP);
        db.execSQL(SlotDAO.TABLE_DROP);
        onCreate(db);
    }

}
