package uqac.dim.muscuboostgraph.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import uqac.dim.muscuboostgraph.core.training.Statistics;

public class StatisticsDAO extends DAOBase {

    public static final String TABLE_NAME = "statistic";

    public static final String KEY = "id";
    public static final String POIDS = "poid";
    public static final String NB_REP = "nb_rep";

    public StatisticsDAO(Context pContext) {
        super(pContext);
    }

    public Statistics insert(Statistics stat) {
        ContentValues value = new ContentValues();
        value.put(StatisticsDAO.POIDS, stat.getPoids());
        value.put(StatisticsDAO.NB_REP, stat.getNb_rep());
        long id = db.insert(MuscleDAO.TABLE_NAME, null, value);
        stat.setId((int)id);
        return stat;
    }

    public Statistics insert(double poids, int nb_rep) {
        ContentValues value = new ContentValues();
        value.put(StatisticsDAO.POIDS, poids);
        value.put(StatisticsDAO.NB_REP, nb_rep);
        long id = db.insert(MuscleDAO.TABLE_NAME, null, value);
        return new Statistics(id, poids, nb_rep);
    }

    public void delete(Statistics muscle) {
        String[] whereArgs = {String.valueOf(muscle.getId())};
        db.delete(TABLE_NAME, KEY + " = ?", whereArgs);
    }

    public void update(Statistics stat) {
        ContentValues value = new ContentValues();
        value.put(StatisticsDAO.POIDS, stat.getPoids());
        value.put(StatisticsDAO.NB_REP, stat.getNb_rep());
        String[] whereArgs = {String.valueOf(stat.getId())};
        db.update(StatisticsDAO.TABLE_NAME, value, StatisticsDAO.KEY + " = ?", whereArgs);
    }

    public DataPoint[] averageWeightForEachDateWhereExercice(List<Integer> list){
        String where = new String();
        for(int i = 0; i < list.size()-1; i++){
            where += list.get(i) + " , ";
        }
        if(list.size() > 0)
            where += list.get(list.size() - 1);

        Log.i("DIM", where.toString());

        String query = "SELECT " + StatExerciseDAO.DATE_ENREGISTREMENT + ", AVG(" + StatisticsDAO.POIDS
                + ") FROM " + StatisticsDAO.TABLE_NAME + ", " + StatExerciseDAO.TABLE_NAME
                + " WHERE " + StatExerciseDAO.STAT_ID + " = " + StatisticsDAO.KEY
                + " AND " + StatExerciseDAO.EXERCISE_ID + " IN ( ? )"
                + " GROUP BY " + StatExerciseDAO.DATE_ENREGISTREMENT;

        Cursor c = db.rawQuery(query, new String[]{where});

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        DataPoint[] serie = new DataPoint[c.getCount()];

        int i = 0;
        while (c.moveToNext() && c.getCount() != 0){
            try {
                serie[i] = new DataPoint(format.parse(c.getString(0)), c.getDouble(1));
                Log.i("DIM", serie[i].getX() + "\t" + serie[i].getY());
                i++;
            }catch (ParseException e) {
                e.printStackTrace();
            }
        }

        c.close();
        return serie;
    }

    public List<Statistics> select(String whereSQL, String[] whereArgs) {
        Cursor c = db.rawQuery("SELECT * FROM " + StatisticsDAO.TABLE_NAME + " WHERE " + whereSQL, whereArgs);
        List<Statistics> array = new ArrayList<>();
        long id;
        double poids;
        int nb_rep;
        while (c.moveToNext()) {
            array.add(new Statistics(c.getLong(0), c.getDouble(1), c.getInt(2)));
        }
        return array;
    }
}
