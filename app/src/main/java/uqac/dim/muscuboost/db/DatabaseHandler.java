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

    private SQLiteDatabase myDataBase;
    private final Context myContext;

    // Database version
    protected static final int VERSION = 41;

    // Database (file) name
    public static final String DATABASE_PATH = "/data/data/uqac.dim.muscuboost/databases/";
    public static final String DATABASE_NAME = "muscu_boost.db";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.myContext = context;
    }

    public void createDatabase() throws IOException {

        Log.i("DIM", "*********createDatabase***********");

        boolean dbExist = checkDataBase();

        if(dbExist) {
            Log.i("DIM", "LA BD EXISTE");
            // By calling this method here onUpgrade will be called on a
            // writeable database, but only if the version number has been
            // bumped
            //onUpgrade(myDataBase, DATABASE_VERSION_old, DATABASE_VERSION);
        }
        else{
            Log.i("DIM", "LA BD N'EXISTE PAS");
            try {
                this.getReadableDatabase();
                this.close();

                Log.i("DIM", "copydatabase");
                copyDataBase();
                Log.i("DIM", "copie effectue avec succes");
            }
            catch (IOException e){
                throw new Error("Error copying database");
            }
        }
    }

    //Check database already exist or not
    private boolean checkDataBase(){

        Log.i("DIM", "checkDataBase");

        boolean checkDB = false;

        try{
            String myPath = DATABASE_PATH + DATABASE_NAME;
            Log.v("DIM", "myPath : " + myPath);

            File dbfile = new File(myPath);
            Log.v("DIM", "dbfile : " + dbfile);
            checkDB = dbfile.exists();
            Log.v("DIM", "checkDB : " + checkDB);
        }
        catch(SQLiteException e){
            Log.v("DIM", "PAS SUPPOSE PASSER ICI JAMAIS");
        }
        return checkDB;
    }

    //Copies your database from your local assets-folder to the just created empty database in the system folder
    private void copyDataBase() throws IOException {

        String outFileName = DATABASE_PATH + DATABASE_NAME;

        Log.i("DIM", "outFileName : " + outFileName);
        Log.i("DIM", "inFileName : " + DATABASE_NAME);

        OutputStream myOutput = new FileOutputStream(outFileName);
        InputStream myInput = myContext.getAssets().open(DATABASE_NAME);

        Log.i("DIM", "myInput : " + myInput);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            String test = "";
            for (byte b:buffer)
                test += (char)b;
            Log.v("DIM", test);

            myOutput.write(buffer, 0, length);
        }
        myInput.close();
        myOutput.flush();
        myOutput.close();
    }

    //delete database
    public void db_delete(){

        File file = new File(DATABASE_PATH + DATABASE_NAME);
        if(file.exists()){
            file.delete();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
