package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.core.schedule.ScheduleSlot;
import uqac.dim.muscuboost.core.training.Training;

public class SlotDAO extends DAOBase {

    public static final String TABLE_NAME = "slot";

    public static final String KEY = "id";
    public static final String DAY = "day";
    public static final String HOUR = "hour";
    public static final String MINUTE = "minute";
    public static final String TRAINING_ID = "training_id";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DAY + " INTEGER NOT NULL,"
                    + HOUR + " INTEGER NOT NULL,"
                    + MINUTE + " INTEGER NOT NULL,"
                    + TRAINING_ID + " INTEGER NOT NULL,"
                    + "FOREIGN KEY (" + TRAINING_ID + ") REFERENCES " + TrainingDAO.TABLE_NAME + "(" + TrainingDAO.KEY + ") ON DELETE CASCADE );";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public SlotDAO(Context pContext) {
        super(pContext);
    }

    public void insert(ScheduleSlot slot) {
        ContentValues value = new ContentValues();
        value.put(DAY, slot.getDay().getId());
        value.put(HOUR, slot.getHour());
        value.put(MINUTE, slot.getMinute());
        value.put(TRAINING_ID, ((Training) slot.getItem()).getId());
        db.insert(MuscleDAO.TABLE_NAME, null, value);
    }

    public void delete(ScheduleSlot slot) {
        delete(slot.getId());
    }

    public void delete(long id) {
        String[] whereArgs = {String.valueOf(id)};
        db.delete(TABLE_NAME, KEY + " = ?", whereArgs);
    }

    public void update(ScheduleSlot slot) {
        ContentValues value = new ContentValues();
        value.put(DAY, slot.getDay().getId());
        value.put(HOUR, slot.getHour());
        value.put(MINUTE, slot.getMinute());
        value.put(TRAINING_ID, ((Training) slot.getItem()).getId());
        String[] whereArgs = {String.valueOf(slot.getId())};
        db.update(TABLE_NAME, value, KEY + " = ?", whereArgs);
    }

    public List<ScheduleSlot> select(String whereSQL, String[] whereArgs) {
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME + whereSQL, whereArgs);
        List<ScheduleSlot> array = new ArrayList<>();
        while (c.moveToNext()) {
            String[] strings = {c.getString(4)};
            Cursor c1 = db.rawQuery("SELECT * FROM " + TrainingDAO.TABLE_NAME + " WHERE " + TrainingDAO.KEY + " = ?", strings);
            c1.moveToNext();
            /*strings = {  };
            Entrainement e = new Entrainement(c1.getString(1), c1.getInt(2), ExerciceDAO.selectionner("WHERE id = ?"));
            Creneau c = new Exercice(m, c.getString(1));
            e.setId(c.getInt(0));
            c1.close();
            array.add(e);*/
        }
        return array;
    }

}
