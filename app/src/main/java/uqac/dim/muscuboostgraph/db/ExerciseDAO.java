package uqac.dim.muscuboostgraph.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboostgraph.core.training.Exercise;
import uqac.dim.muscuboostgraph.core.training.Muscle;

public class ExerciseDAO extends DAOBase {

    public static final String TABLE_NAME = "exercise";

    public static final String KEY = "id";
    public static final String NAME = "name";
    public static final String MUSCLE_ID = "muscle_id";
    public static final String DESCRIPTION = "description";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME + " VARCHAR(30) NOT NULL,"
                    + MUSCLE_ID + " INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + MUSCLE_ID + ") REFERENCES " + MuscleDAO.TABLE_NAME + "(" + MuscleDAO.KEY + ") );";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public ExerciseDAO(Context pContext) {
        super(pContext);
    }

    public Exercise insert(String name, Muscle muscle, String description) {
        ContentValues value = new ContentValues();
        value.put(NAME, name);
        value.put(MUSCLE_ID, muscle.getId());
        value.put(DESCRIPTION, description );
        long id = db.insert(ExerciseDAO.TABLE_NAME, null, value);
        return new Exercise(id, name,  muscle);
    }

    public void delete(Exercise exercise) {
        String[] whereArgs = {String.valueOf(exercise.getId())};
        db.delete(TABLE_NAME, KEY + " = ?", whereArgs);
    }

    public void update(Exercise exercise) {
        ContentValues value = new ContentValues();
        value.put(NAME, exercise.getName());
        value.put(MUSCLE_ID, exercise.getMuscle().getId());
        value.put(DESCRIPTION, exercise.getDescription());
        String[] whereArgs = {String.valueOf(exercise.getId())};
        db.update(TABLE_NAME, value, KEY + " = ?", whereArgs);
    }

    public List<String> selectAllName(){
        Cursor c = db.rawQuery("SELECT " + NAME + " FROM " + TABLE_NAME , null);
        List<String> array = new ArrayList<String>();
        while (c.moveToNext()) {
            array.add(c.getString(0));
        }
        c.close();
        return array;
    }

    public List<Integer> selectAllIdWhereMuscle(int id){
        Cursor c = db.rawQuery("SELECT " + KEY + " FROM " + TABLE_NAME + " WHERE " + MUSCLE_ID + " = ? ", new String[] {""+id} );
        List<Integer> array = new ArrayList<Integer>();
        while (c.moveToNext()){
            array.add(c.getInt(0));
        }
        c.close();
        return array;
    }

    public static List<Exercise> select(String whereSQL, String[] whereArgs) {
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + whereSQL, whereArgs);
        List<Exercise> array = new ArrayList<>();
        while (c.moveToNext() && c.getCount() != 0) {
            String[] strings = {c.getString(2)};
            Cursor c1 = db.rawQuery("SELECT * FROM " + MuscleDAO.TABLE_NAME + " WHERE " + MuscleDAO.KEY + " = ?", strings);
            c1.moveToNext();
            long id = c.getLong(0);
            String name = c.getString(1);
            Muscle m = new Muscle(c1.getInt(0), c1.getString(1));
            Exercise e = new Exercise(id, name, m);
            c1.close();
            array.add(e);
        }
        return array;
    }
}
