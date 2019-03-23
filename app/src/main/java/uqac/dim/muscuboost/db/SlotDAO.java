package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.core.schedule.Day;
import uqac.dim.muscuboost.core.schedule.ScheduleSlot;
import uqac.dim.muscuboost.core.training.Training;

public class SlotDAO extends DAOBase {
    private TrainingDAO trainingDao = new TrainingDAO(context);

    public static final String TABLE_NAME = "slot";

    public static final String KEY = "id";
    public static final String DAY = "day";
    public static final String HOUR = "hour";
    public static final String MINUTE = "minute";
    public static final String TRAINING_ID = "training_id";

    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + DAY + " INTEGER,"
                    + HOUR + " INTEGER,"
                    + MINUTE + " INTEGER NOT NULL,"
                    + TRAINING_ID + " INTEGER,"
                    + "FOREIGN KEY (" + TRAINING_ID + ") REFERENCES " + TrainingDAO.TABLE_NAME + "(" + TrainingDAO.KEY + ") ON DELETE CASCADE );";

    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public SlotDAO(Context context) {
        super(context);
    }

    @Override
    public SQLiteDatabase open() {
        trainingDao.open();
        return super.open();
    }

    @Override
    public void close() {
        super.close();
        trainingDao.close();
    }

    public ScheduleSlot addSlot(Day day, int hour, int minute, Training training) {
        ContentValues values = new ContentValues();
        values.put(DAY, day.getId());
        values.put(HOUR, hour);
        values.put(MINUTE, minute);
        values.put(TRAINING_ID, training.getId());
        long id = db.insert(MuscleDAO.TABLE_NAME, null, values);
        return new ScheduleSlot(id, day, hour, minute, training);
    }

    public void removeSlot(ScheduleSlot slot) {
        String[] whereArgs = {String.valueOf(slot.getId())};
        db.delete(TABLE_NAME, KEY + " = ?", whereArgs);
    }

    public void updateSlot(ScheduleSlot slot) {
        ContentValues values = new ContentValues();
        values.put(DAY, slot.getDay().getId());
        values.put(HOUR, slot.getHour());
        values.put(MINUTE, slot.getMinute());
        values.put(TRAINING_ID, ((Training) slot.getItem()).getId());
        String[] whereArgs = {String.valueOf(slot.getId())};
        db.update(TABLE_NAME, values, KEY + " = ?", whereArgs);
    }

    public List<ScheduleSlot> getSlots() {
        return getSlots(null, null);
    }

    public List<ScheduleSlot> getSlots(String whereSQL, String[] whereArgs) {
        Cursor c = db.rawQuery("SELECT * "
                        + "FROM " + TABLE_NAME
                        + (whereSQL != null ? " WHERE " + whereSQL : ""),
                whereArgs);
        List<ScheduleSlot> slots = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(KEY));
            int day = c.getInt(c.getColumnIndex(DAY));
            int hour = c.getInt(c.getColumnIndex(HOUR));
            int minute = c.getInt(c.getColumnIndex(MINUTE));
            int training_id = c.getInt(c.getColumnIndex(TRAINING_ID));
            Training training = trainingDao.getTraining(training_id);
            slots.add(new ScheduleSlot(id, Day.getDayById(day), hour, minute, training));
        }
        return slots;
    }

    public ScheduleSlot getSlot(long slotId) {
        String[] whereArgs = {String.valueOf(slotId)};
        List<ScheduleSlot> slots = getSlots(KEY + " = ?", whereArgs);
        return !slots.isEmpty() ? slots.get(0) : null;
    }

}
