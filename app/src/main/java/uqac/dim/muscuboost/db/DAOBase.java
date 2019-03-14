package uqac.dim.muscuboost.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAOBase {
    // Version de la base
    protected static final int VERSION = 8;

    // Nom de la base
    protected static final String NOM_BASE = "muscuboost.db";

    protected static SQLiteDatabase bdd = null;
    protected static DatabaseHandler gestion = null;

    public DAOBase(Context pContext) {
        this.gestion = new DatabaseHandler(pContext, NOM_BASE, null, VERSION);
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
