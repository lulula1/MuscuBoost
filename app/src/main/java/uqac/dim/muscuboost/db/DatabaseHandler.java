package uqac.dim.muscuboost.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {


    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MuscleDAO.TABLE_CREATE);
        db.execSQL(ExerciceDAO.TABLE_CREATE);
        db.execSQL(EntrainementDAO.TABLE_CREATE);
        db.execSQL(EntrainementExerciceDAO.TABLE_CREATE);
        db.execSQL(CreneauDAO.TABLE_CREATE);

        db.execSQL("INSERT INTO muscle(intitule) VALUES ('Deltoide')");
        db.execSQL("INSERT INTO muscle(intitule) VALUES ('Biceps')");
        db.execSQL("INSERT INTO muscle(intitule) VALUES ('Triceps')");
        db.execSQL("INSERT INTO muscle(intitule) VALUES ('Pectauraux')");

        db.execSQL("INSERT INTO exercice(intitule, muscle) VALUES ('Develloper-Coucher', 4)");
        db.execSQL("INSERT INTO exercice(intitule, muscle) VALUES ('Haltere', 2)");
        db.execSQL("INSERT INTO exercice(intitule, muscle) VALUES ('Tractions', 3)");
        db.execSQL("INSERT INTO exercice(intitule, muscle) VALUES ('Deeps', 1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(MuscleDAO.TABLE_DROP);
        db.execSQL(ExerciceDAO.TABLE_DROP);
        db.execSQL(EntrainementDAO.TABLE_DROP);
        db.execSQL(EntrainementExerciceDAO.TABLE_DROP);
        db.execSQL(CreneauDAO.TABLE_DROP);
        onCreate(db);
    }
}
