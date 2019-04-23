package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.core.schedule.Day;
import uqac.dim.muscuboost.core.schedule.ScheduleSlot;
import uqac.dim.muscuboost.core.training.Training;

public class SlotDAO extends DAOSingleKey<ScheduleSlot> {
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
                    + "FOREIGN KEY (" + TRAINING_ID + ") REFERENCES "
                    + TrainingDAO.TABLE_NAME + "(" + TrainingDAO.KEY + ") ON DELETE CASCADE );";

    public SlotDAO(Context context) {
        super(context, TABLE_NAME, KEY);
    }

    @Override
    public void open() {
        super.open();
        trainingDao.open();
    }

    @Override
    public void close() {
        super.close();
        trainingDao.close();
    }

    public ScheduleSlot insert(Day day, Training training) {
        return insert(day, -1, -1, training);
    }

    public ScheduleSlot insert(Day day, int hour, int minute, Training training) {
        ContentValues values = new ContentValues();
        values.put(DAY, day.getId());
        if(hour >= 0 && hour <= 23)
            values.put(HOUR, hour);
        if(minute >= 0 && minute <= 59)
            values.put(MINUTE, minute);
        values.put(TRAINING_ID, training.getId());
        long id = db.insert(TABLE_NAME, null, values);
        return new ScheduleSlot(id, day, hour, minute, training);
    }

    @Override
    public void update(ScheduleSlot slot) {
        ContentValues values = new ContentValues();
        values.put(DAY, slot.getDay().getId());
        if(slot.getHour() != null)
            values.put(HOUR, slot.getHour());
        if(slot.getMinute() != null)
            values.put(MINUTE, slot.getMinute());
        values.put(TRAINING_ID, ((Training) slot.getItem()).getId());
        String[] whereArgs = {String.valueOf(slot.getId())};
        db.update(TABLE_NAME, values, KEY + " = ?", whereArgs);
    }

    @Override
    public List<ScheduleSlot> getAll(String whereSQL, String[] whereArgs) {
        Cursor c = getGetAllCursor(whereSQL, whereArgs);
        List<ScheduleSlot> slots = new ArrayList<>();
        while (c.moveToNext()) {
            long id = c.getLong(c.getColumnIndex(KEY));
            Integer day = !c.isNull(c.getColumnIndex(DAY))
                    ? c.getInt(c.getColumnIndex(DAY))
                    : null;
            Integer hour = !c.isNull(c.getColumnIndex(DAY))
                    ? c.getInt(c.getColumnIndex(HOUR))
                    : null;
            int minute = c.getInt(c.getColumnIndex(MINUTE));
            int training_id = c.getInt(c.getColumnIndex(TRAINING_ID));
            Training training = trainingDao.get(training_id);
            slots.add(new ScheduleSlot(id, Day.getDayById(day), hour, minute, training));
        }
        return slots;
    }

    public boolean isTrainingInUse(long trainingId) {
        return !getAll(SlotDAO.TRAINING_ID + " = ?",
                new String[]{String.valueOf(trainingId)}).isEmpty();
    }

}
