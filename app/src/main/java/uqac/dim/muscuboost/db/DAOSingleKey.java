package uqac.dim.muscuboost.db;

import android.content.Context;
import android.database.Cursor;

import java.util.List;

public abstract class DAOSingleKey<T> extends DAOBase {

    private final String TABLE_NAME;
    private final String KEY;

    public DAOSingleKey(Context context, String tableName, String key) {
        super(context);
        TABLE_NAME = tableName;
        KEY = key;
    }

    public void delete(long id) {
        String[] whereArgs = {String.valueOf(id)};
        db.delete(TABLE_NAME, KEY + " = ?", whereArgs);
    }

    public abstract void update(T object);

    public abstract List<T> getAll(String whereSQL, String[] whereArgs);

    public List<T> getAll() {
        return getAll(null, null);
    }

    public T get(long id) {
        String[] whereArgs = {String.valueOf(id)};
        List<T> array = getAll(KEY + " = ?", whereArgs);
        return !array.isEmpty() ? array.get(0) : null;
    }

    protected Cursor getGetAllCursor(String whereSQL, String[] whereArgs, String orderBy) {
        return db.rawQuery("SELECT * "
                        + "FROM " + TABLE_NAME
                        + (whereSQL != null ? " WHERE " + whereSQL : "")
                        + (orderBy != null ? " ORDER BY " + orderBy : "")
                        + ";",
                whereArgs);
    }

    protected Cursor getGetAllCursor(String whereSQL, String[] whereArgs) {
        return getGetAllCursor(whereSQL, whereArgs, null);
    }

}
