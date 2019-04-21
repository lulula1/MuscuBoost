package uqac.dim.muscuboostgraph.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboostgraph.core.training.Muscle;

public class MuscleDAO extends DAOBase {
    public static final String TABLE_NAME = "muscle";

    public static final String KEY = "id";
    public static final String NAME = "name";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + NAME + " VARCHAR(30) NOT NULL );";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public MuscleDAO(Context pContext) {
        super(pContext);
    }

    public Muscle insert(Muscle muscle) {
        ContentValues value = new ContentValues();
        value.put(MuscleDAO.NAME, muscle.getName());
        long id = db.insert(MuscleDAO.TABLE_NAME, null, value);
        muscle.setId((int)id);
        return muscle;
    }

    public Muscle insert(String name) {
        ContentValues value = new ContentValues();
        value.put(MuscleDAO.NAME, name);
        long id = db.insert(MuscleDAO.TABLE_NAME, null, value);
        return new Muscle((int)id, name);
    }

    public void delete(Muscle muscle) {
        String[] whereArgs = {String.valueOf(muscle.getId())};
        db.delete(TABLE_NAME, KEY + " = ?", whereArgs);
    }

    public void update(Muscle muscle) {
        ContentValues value = new ContentValues();
        value.put(NAME, muscle.getName());
        String[] whereArgs = {String.valueOf(muscle.getId())};
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
