package uqac.dim.muscuboost.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAOBase {

    protected static SQLiteDatabase bdd = null;
    private static DatabaseHandler gestion = null;

    public DAOBase(Context pContext) {
        this.gestion = new DatabaseHandler(pContext);
    }

    public SQLiteDatabase open() {
        bdd = gestion.getWritableDatabase();
        return bdd;
    }

    public void close() {
        bdd.close();
        bdd = null;
    }

    public SQLiteDatabase getBdd() {
        return bdd;
    }
}
