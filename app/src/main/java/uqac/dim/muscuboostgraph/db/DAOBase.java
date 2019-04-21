package uqac.dim.muscuboostgraph.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public abstract class DAOBase {

    protected static SQLiteDatabase db = null;
    private static DatabaseHandler handler = null;

    public DAOBase(Context pContext) {
        handler = new DatabaseHandler(pContext);
    }

    public SQLiteDatabase open() {
        db = handler.getWritableDatabase();

        return db;
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
