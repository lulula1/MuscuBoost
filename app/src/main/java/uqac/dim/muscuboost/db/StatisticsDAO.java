package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.jjoe64.graphview.series.DataPoint;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.core.training.Statistics;

public class StatisticsDAO extends DAOSingleKey<Statistics> {
    public static final String TABLE_NAME = "statistic";

    public static final String KEY = "id";
    public static final String WEIGHT = "weight";
    public static final String REP_COUNT = "rep_count";

    public StatisticsDAO(Context context) {
        super(context, TABLE_NAME, KEY);
    }

    public Statistics insert(double weight, int repCount) {
        ContentValues values = new ContentValues();
        values.put(WEIGHT, weight);
        values.put(REP_COUNT, repCount);
        long id = db.insert(TABLE_NAME, null, values);
        return new Statistics(id, weight, repCount);
    }

    @Override
    public List<Statistics> getAll(String whereSQL, String[] whereArgs) {
        Cursor c = getGetAllCursor(whereSQL, whereArgs);
        List<Statistics> statistics = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(KEY));
            double weight = c.getDouble(c.getColumnIndex(WEIGHT));
            int repCount = c.getInt(c.getColumnIndex(REP_COUNT));
            statistics.add(new Statistics(id, weight, repCount));
        }
        return statistics;
    }

    @Override
    public void update(Statistics statistics) {
        ContentValues values = new ContentValues();
        values.put(WEIGHT, statistics.getWeight());
        values.put(REP_COUNT, statistics.getRepCount());
        String[] whereArgs = {String.valueOf(statistics.getId())};
        db.update(TABLE_NAME, values, KEY + " = ?", whereArgs);
    }

    public DataPoint[] getAvgWeightForEachDate(List<Integer> list) {
        StringBuilder where = new StringBuilder();
        for (int i = 0; i < list.size(); i++)
            where.append(list.get(i) + (i < list.size()-1 ? ", " : ""));

        String query = "SELECT " + StatExerciseDAO.RECORD_DATE + ", AVG(" + StatisticsDAO.WEIGHT + ")"
                + " FROM " + TABLE_NAME + " S INNER JOIN " + StatExerciseDAO.TABLE_NAME + " Se"
                + " ON S." + KEY + " = Se." + StatExerciseDAO.STAT_ID
                + " WHERE " + StatExerciseDAO.EXERCISE_ID + " IN ( " + where.toString() + " )"
                + " GROUP BY " + StatExerciseDAO.RECORD_DATE + ";";
        return getAvg(query, null);
    }

    public DataPoint[] getAvgRepCountForEachDate(int exerciseId) {
        String query = "SELECT " + StatExerciseDAO.RECORD_DATE + ", AVG(" + StatisticsDAO.REP_COUNT + ")"
                + " FROM " + TABLE_NAME + " S INNER JOIN " + StatExerciseDAO.TABLE_NAME + " Se"
                + " ON S." + KEY + " = Se." + StatExerciseDAO.STAT_ID
                + " WHERE " + StatExerciseDAO.EXERCISE_ID + " = ? "
                + " GROUP BY " + StatExerciseDAO.RECORD_DATE + ";";
        return getAvg(query, new String[]{String.valueOf(exerciseId)});
    }

    private DataPoint[] getAvg(String query, String[] whereArgs) {
        Cursor c = db.rawQuery(query, whereArgs);

        DateFormat format = DateFormat.getDateInstance();
        DataPoint[] dataPoint = new DataPoint[c.getCount()];

        int i = 0;
        while (c.moveToNext()) {
            try {
                dataPoint[i++] = new DataPoint(format.parse(c.getString(0)),
                        c.getDouble(1));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return dataPoint;
    }

}
