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

    public MuscleDAO(Context context) {
        super(context, TABLE_NAME, KEY);
    }

    public Muscle insert(String name) {
        ContentValues values = new ContentValues();
        values.put(MuscleDAO.NAME, name);
        long id = db.insert(MuscleDAO.TABLE_NAME, null, values);
        return new Muscle(id, name);
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

    public List<String> getAllName() {
        List<String> names = new ArrayList<>();
        for(Muscle muscle : getAll())
            names.add(muscle.getName());
        return names;
    }

    public Muscle getName(String name){
        for(Muscle muscle : getAll())
            if(muscle.getName().equals(name))
                return muscle;
        return null;
    }

    public long getId(String name){
        for(Muscle muscle : getAll())
            if(muscle.getName().equals(name))
                return muscle.getId();
        return -1;
    }

}
