package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.jjoe64.graphview.series.DataPoint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uqac.dim.muscuboost.core.person.Body;

public class BodyDAO extends DAOBase {

    public static final String TABLE_NAME = "body";

    public static final String WEIGHT = "weight";
    public static final String HEIGHT = "height";
    public static final String REGISTER_DATE = "register_date";

    public BodyDAO(Context context) {
        super(context);
    }

    public Body insert(double masse, double taille) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ContentValues values = new ContentValues();
        values.put(WEIGHT, masse);
        values.put(HEIGHT, taille);
        Date d = new Date();
        values.put(REGISTER_DATE, df.format(d));
        db.insert(TABLE_NAME, null, values);
        return new Body(masse, taille,d);
    }

    public int nbValeur(){
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        int nbResult = c.getCount();
        c.close();
        return nbResult;
    }

    public DataPoint[] getAll(){
        Cursor c = db.rawQuery("SELECT "+ REGISTER_DATE + ", "+ WEIGHT +" FROM "+ TABLE_NAME + " ORDER BY "+ REGISTER_DATE, null);
        DataPoint[] datas = new DataPoint[c.getCount()];
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        int i = 0;
        while(c.moveToNext()){
            try {
                datas[i] = new DataPoint(df.parse(c.getString(0)), c.getDouble(1));
                Log.i("DIM", datas[i].getX()+"\t"+datas[i].getY());
                i++;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        c.close();
        return datas;
    }

    public Body selectLast(){
        Cursor c =db.rawQuery("SELECT * FROM " + TABLE_NAME
                + " WHERE " + REGISTER_DATE + " = " +
                "(SELECT MAX("+ REGISTER_DATE +") FROM "+ TABLE_NAME +" )", null);

        c.moveToFirst();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date d = null;
        try {
            d = df.parse(c.getString(2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Body b = new Body(c.getDouble(0), c.getDouble(1), d);
        c.close();
        return b;
    }

}
