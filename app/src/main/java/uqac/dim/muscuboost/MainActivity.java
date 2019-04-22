package uqac.dim.muscuboost;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.io.IOException;

import uqac.dim.muscuboost.AjoutExercice.ListeExerciseFragment;
import uqac.dim.muscuboost.db.DatabaseHandler;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        try{
           // db.db_delete();
            db.createDatabase();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void clickChrono(View v) {
        startActivity( new Intent(getBaseContext(), Chronometre.class));
    }

    public void clickEntrainement(View v) {
        startActivity(new Intent(getBaseContext(), ScheduleActivity.class));
    }

    public void clickExercice(View v) {
        startActivity(new Intent(getBaseContext(), ListeExerciseFragment.class));
    }
}
