package uqac.dim.muscuboost.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uqac.dim.muscuboost.core.person.Person;

public class PersonDAO extends DAOBase {
    public static final String TABLE_NAME = "person";

    public static final String NAME = "name";
    public static final String SURNAME = "surname";
    public static final String DATE_BIRTH = "date_birth";

    public PersonDAO(Context context) {
        super(context);
    }

    public Person insert(String name, String surname, Date date_birth) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ContentValues values = new ContentValues();
        values.put(PersonDAO.NAME, name);
        values.put(PersonDAO.SURNAME, surname);
        values.put(PersonDAO.DATE_BIRTH, df.format(date_birth));
        db.insert(TABLE_NAME, null, values);
        return new Person(name, surname, date_birth);
    }

    public void update(Person person) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        ContentValues values = new ContentValues();
        values.put(NAME, person.getName());
        values.put(SURNAME, person.getSurname());
        values.put(DATE_BIRTH, df.format(person.getDate_naissance()));
        String[] whereArgs = {String.valueOf(person.getName())};
        db.update(TABLE_NAME, values, NAME + " = ?", whereArgs);
    }

    public Person select(){
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null );
        c.moveToFirst();
        Log.i("DIM", c.getString(0));
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date_birth = new Date();
        try {
            date_birth = df.parse(c.getString(2));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String nom = c.getString(0);
        String prenom = c.getString(1);
        c.close();
        return new Person(nom, prenom, date_birth);
    }

    public String selectName(){
        Cursor c = db.rawQuery("SELECT " + SURNAME + " FROM " + TABLE_NAME, null );
        c.moveToFirst();
        String surname = c.getString(0);
        c.close();
        return surname;
    }

    public boolean isRegistered(){
        Cursor c = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        int count = c.getCount();
        c.close();
        if(count == 1)
            return true;
        else
            return false;
    }
}
