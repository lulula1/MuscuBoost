package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

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
    public static final String DESCRIPTION = "description";

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

    public Exercise insert(String name, Muscle muscle, String description) {
        ContentValues value = new ContentValues();
        value.put(NAME, name);
        value.put(MUSCLE_ID, muscle.getId());
        value.put(DESCRIPTION, description );
        long id = db.insert(ExerciseDAO.TABLE_NAME, null, value);
        return new Exercise(id, name,  muscle, description);
    }

    @Override
    public void update(Exercise exercise) {
        ContentValues values = new ContentValues();
        values.put(NAME, exercise.getName());
        values.put(MUSCLE_ID, exercise.getMuscle().getId());
        values.put(DESCRIPTION, exercise.getDescription());
        String[] whereArgs = {String.valueOf(exercise.getId())};
        db.update(TABLE_NAME, values, KEY + " = ?", whereArgs);
    }

    @Override
    public List<Exercise> getAll(String whereSQL, String[] whereArgs) {
        Cursor c = getGetAllCursor(whereSQL, whereArgs, NAME);
        List<Exercise> exercises = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(KEY));
            String name = c.getString(c.getColumnIndex(NAME));
            long muscleId = c.getLong(c.getColumnIndex(MUSCLE_ID));
            String description = c.getString(c.getColumnIndex(DESCRIPTION));
            Muscle muscle = muscleDao.get(muscleId);
            exercises.add(new Exercise(id, name, muscle, description));
        }
        return exercises;
    }

    public List<String> getAllNames() {
        List<String> names = new ArrayList<>();
        for(Exercise exercise : getAll())
            names.add(exercise.getName());
        return names;
    }

    public Exercise getFromName(String name) {
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ NAME +" = ?;",
                new String[] {name});
        c.moveToFirst();
        long id = c.getInt(c.getColumnIndex(KEY));
        String exName = c.getString(c.getColumnIndex(NAME));
        Muscle muscle = muscleDao.get(c.getInt(c.getColumnIndex(MUSCLE_ID)));
        String description = c.getString(c.getColumnIndex(DESCRIPTION));
        c.close();
        return new Exercise(id, exName, muscle, description);
    }

    public List<Integer> getAllIdFromMuscleId(long id){
        Cursor c = db.rawQuery("SELECT " + KEY + " FROM " + TABLE_NAME
                + " WHERE " + MUSCLE_ID + " = ?;", new String[] {String.valueOf(id)} );
        List<Integer> array = new ArrayList<>();
        while (c.moveToNext())
            array.add(c.getInt(0));
        c.close();
        return array;
    }

    public long getIdFromName(String name){
        Exercise exercise = getFromName(name);
        return exercise != null ? exercise.getId() : -1;
    }
}
