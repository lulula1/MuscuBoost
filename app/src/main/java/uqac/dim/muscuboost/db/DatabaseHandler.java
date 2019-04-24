package uqac.dim.muscuboost.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final Context context;

    // Database version
    protected static final int VERSION = 60;

    // Database (file) name
    public static final String DATABASE_PATH = "/data/data/uqac.dim.muscuboost/databases/";
    public static final String DATABASE_NAME = "muscu_boost.db";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
    }

    public void createDatabase() {
        boolean dbExists = checkDataBaseExists();

        if (dbExists) {
            Log.i("DIM", "DB EXISTS");
        } else {
            Log.i("DIM", "DB DOESN'T EXISTS");
            try {
                super.getWritableDatabase();
                this.close();

                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    public boolean checkDataBaseExists() {
        try {
            File dbFile = new File(DATABASE_PATH + DATABASE_NAME);
            return dbFile.exists();
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void copyDataBase() throws IOException {
        String outputPath = DATABASE_PATH + DATABASE_NAME;

        InputStream input = context.getAssets().open(DATABASE_NAME);
        OutputStream output = new FileOutputStream(outputPath);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0)
            output.write(buffer, 0, length);

        input.close();
        output.flush();
        output.close();
    }

    public void dbDelete() {
        File file = new File(DATABASE_PATH + DATABASE_NAME);
        if (file.exists())
            if (!file.delete()) {
                Log.i("DIM", "DB DELETE FAILED");
                return;
            }
        Log.i("DIM", "DB DELETED");
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        if (!db.isReadOnly())
            db.execSQL("PRAGMA foreign_keys = ON");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
