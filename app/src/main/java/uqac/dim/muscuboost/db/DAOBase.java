package uqac.dim.muscuboost.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAOBase {

    protected Context context;

    protected SQLiteDatabase db;
    private DatabaseHandler handler;

    public DAOBase(Context context) {
        this.context = context;
        handler = new DatabaseHandler(context);
    }

    public void open() {
        db = handler.getWritableDatabase();
    }

    public void close() {
        db.close();
        db = null;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public DatabaseHandler getDatabaseHandler(){
        return handler;
    }

}
