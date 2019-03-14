package uqac.dim.muscuboost.db;

import android.content.Context;

public class EntrainementExerciceDAO extends DAOBase {

    public static final String TABLE_NAME = "ligne_entrainement";

    public static final String ID_ENTRAINEMENT = "id_entrainement";
    public static final String ID_EXERCICE = "id_exercice";

    protected static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + ID_ENTRAINEMENT + " INTEGER,"
                    + ID_EXERCICE + " INTEGER,"
                    + "PRIMARY KEY (" + ID_ENTRAINEMENT + ", " + ID_EXERCICE + "),"
                    + "FOREIGN KEY (" + ID_ENTRAINEMENT + ") REFERENCES " + EntrainementDAO.TABLE_NAME + "(" + EntrainementDAO.KEY + ") ON DELETE CASCADE,"
                    + "FOREIGN KEY (" + ID_EXERCICE + ") REFERENCES " + ExerciceDAO.TABLE_NAME + "(" + ExerciceDAO.KEY + ") ON DELETE CASCADE );";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public EntrainementExerciceDAO(Context pContext) {
        super(pContext);
    }

}
