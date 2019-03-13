package uqac.dim.muscuboost;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public abstract class DAOBase {
    // Version de la base
    protected final static int VERSION = 7;

    // Nom de la base
    protected  final static String NOM_BASE = "muscuboost.db";

    protected final static String LIGNE_ENT = "ligne_entrainement";
    protected final static String CREATE_LIGNE_ENT =
            "CREATE TABLE " + LIGNE_ENT + " ("
            + "id_entrainement INTEGER,"
            + "id_exercice INTEGER,"
            + "FOREIGN KEY (id_entrainement) REFERENCES " + EntrainementDAO.TABLE_NAME + "(" + EntrainementDAO.KEY + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (id_exercice) REFERENCES " + ExerciceDAO.TABLE_NAME + "(" + ExerciceDAO.KEY + ") ON DELETE CASCADE );";
    public static final String DROP_LIGNE_ENT =  "DROP TABLE IF EXISTS " + LIGNE_ENT + ";";


    protected static SQLiteDatabase bdd = null;
    protected static DatabaseHandler gestion = null;

    public DAOBase(Context pContext) {
        this.gestion = new DatabaseHandler(pContext, NOM_BASE, null, VERSION);
    }

    public SQLiteDatabase open(){
        bdd = gestion.getWritableDatabase();
        return bdd;
    }

    public void close(){
        bdd.close();
    }

    public SQLiteDatabase getBdd(){
        return bdd;
    }
}
