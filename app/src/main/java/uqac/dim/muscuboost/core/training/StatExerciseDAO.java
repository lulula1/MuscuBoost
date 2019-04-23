package uqac.dim.muscuboost.core.training;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.db.DAOBase;

public class StatExerciseDAO extends DAOBase {
    public static final String TABLE_NAME = "stat_exercise";

    public static final String TRAINING_ID = "training_id";
    public static final String EXERCISE_ID = "exercise_id";
    public static final String STAT_ID = "stat_id";
    public static final String DATE_ENREGISTREMENT = "date_enregistrement";

    public StatExerciseDAO(Context pContext) {
        super(pContext);
    }

    public void insert(Training training, Exercise exercise, Statistics statistics) {
        ContentValues value = new ContentValues();
        value.put(StatExerciseDAO.TRAINING_ID, training.getId());
        value.put(StatExerciseDAO.EXERCISE_ID, exercise.getId());
        value.put(StatExerciseDAO.STAT_ID, statistics.getId());
        value.put(StatExerciseDAO.DATE_ENREGISTREMENT, "0000-00-00");
        db.insert(StatExerciseDAO.TABLE_NAME, null, value);
    }

    public void delete(Training training, Exercise exercise) {
        String[] whereArgs = {String.valueOf(training.getId()),
                String.valueOf(exercise.getId())};
        db.delete(StatExerciseDAO.TABLE_NAME,
                StatExerciseDAO.TRAINING_ID + " = ? AND"
                        + StatExerciseDAO.EXERCISE_ID + " = ?", whereArgs);
    }

    public List<Integer> selectAllIdWhereExercise(List<Integer> list){
        StringBuilder where = new StringBuilder();
        for(int i = 0; i < list.size()-1; i++){
            where.append(list.get(i)).append(",");
        }
        where.append(list.get(list.size() - 1));

        Cursor c = db.rawQuery("SELECT " + STAT_ID + " FROM " + TABLE_NAME + " WHERE " + EXERCISE_ID + " IN ( ? ) ", new String[] {where.toString()} );
        List<Integer> array = new ArrayList<Integer>();
        while (c.moveToNext()){
            array.add(c.getInt(0));
        }
        c.close();
        return array;
    }
}
