package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.core.training.Muscle;

public class MuscleDAO extends DAOSingleKey<Muscle> {

    public static final String TABLE_NAME = "muscle";

    public static final String KEY = "id";
    public static final String NAME = "name";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME + " VARCHAR(30) NOT NULL );";

    public MuscleDAO(Context context) {
        super(context, TABLE_NAME, KEY);
    }

    public Muscle insert(String name) {
        ContentValues values = new ContentValues();
        values.put(MuscleDAO.NAME, name);
        long id = db.insert(MuscleDAO.TABLE_NAME, null, values);
        return new Muscle(id, name);
    }

    public void delete(Muscle muscle) {
        String[] whereArgs = {String.valueOf(muscle.getId())};
        db.delete(TABLE_NAME, KEY + " = ?", whereArgs);
    }

    @Override
    public void update(Muscle muscle) {
        ContentValues values = new ContentValues();
        values.put(NAME, muscle.getName());
        String[] whereArgs = {String.valueOf(muscle.getId())};
        db.update(TABLE_NAME, values, KEY + " = ?", whereArgs);
    }

    @Override
    public List<Muscle> getAll(String whereSQL, String[] whereArgs) {
        Cursor c = getGetAllCursor(whereSQL, whereArgs);
        List<Muscle> muscles = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(KEY));
            String name = c.getString(c.getColumnIndex(NAME));
            muscles.add(new Muscle(id, name));
        }
        return muscles;
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

    public Muscle selectName(String name){
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE "+ NAME +" = ?", new String[] {name});
        c.moveToFirst();
        Muscle m = new Muscle(c.getInt(0),c.getString(1));
        c.close();
        return m;
    }

    public int selectIdOf(String name){
        Cursor c = db.rawQuery("SELECT " + KEY + " FROM " + TABLE_NAME + " WHERE " + NAME + " = ?", new String[] {name});
        c.moveToNext();
        int res = c.getInt(0);
        c.close();
        return res;
    }

    public List<Muscle> select(String whereSQL, String[] whereArgs) {
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + whereSQL, whereArgs);
        List<Muscle> array = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(0);
            String name = c.getString(1);
            Muscle m = new Muscle((int)id, name);
            array.add(m);
        }
        return array;
    }

}
