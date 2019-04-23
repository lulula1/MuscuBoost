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
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if(!db.isReadOnly())
            db.execSQL("PRAGMA foreign_keys = ON");
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
        db.execSQL("INSERT INTO " + ExerciseDAO.TABLE_NAME + "(" + ExerciseDAO.NAME + "," + ExerciseDAO.MUSCLE_ID + ") VALUES ('Dips', 1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MuscleDAO.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + ExerciseDAO.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TrainingDAO.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TrainingExerciseDAO.TABLE_NAME + ";");
        db.execSQL("DROP TABLE IF EXISTS " + SlotDAO.TABLE_NAME + ";");
        onCreate(db);
    }

}
