package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StatExerciseDAO extends DAOBase {
    public static final String TABLE_NAME = "stat_exercise";

    public static final String TRAINING_ID = "training_id";
    public static final String EXERCISE_ID = "exercise_id";
    public static final String STAT_ID = "stat_id";
    public static final String RECORD_DATE = "record_date";

    public StatExerciseDAO(Context context) {
        super(context);
    }

    public void insert(long trainingId, long exerciseId, long statisticsId) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ContentValues values = new ContentValues();
        values.put(TRAINING_ID, trainingId);
        values.put(EXERCISE_ID, exerciseId);
        values.put(STAT_ID, statisticsId);
        values.put(RECORD_DATE, df.format(new Date()));
        db.insert(TABLE_NAME, null, values);
    }

    public void delete(long trainingId, long exerciseId, long statisticsId) {
        String[] whereArgs = {String.valueOf(trainingId),
                String.valueOf(exerciseId), String.valueOf(statisticsId)};
        db.delete(TABLE_NAME, TRAINING_ID + " = ? AND"
                        + EXERCISE_ID + " = ? AND " + STAT_ID + " = ?", whereArgs);
    }

    public List<Integer> getAllIdWhereExercise(List<Integer> list){
        StringBuilder where = new StringBuilder();
        for (int i = 0; i < list.size(); i++)
            where.append(list.get(i) + (i < list.size()-1 ? ", " : ""));

        Cursor c = db.rawQuery("SELECT " + STAT_ID + " FROM " + TABLE_NAME
                + " WHERE " + EXERCISE_ID + " IN ( " + where.toString() + " );", null);
        List<Integer> array = new ArrayList<>();
        while (c.moveToNext())
            array.add(c.getInt(0));
        c.close();
        return array;
    }
}
