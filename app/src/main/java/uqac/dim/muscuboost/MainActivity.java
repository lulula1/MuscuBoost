package uqac.dim.muscuboost;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import java.io.IOException;

import uqac.dim.muscuboost.AjoutExercice.ListeExerciseFragment;
import uqac.dim.muscuboost.db.DatabaseHandler;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        try{
           // db.db_delete();
            db.createDatabase();
        }catch(IOException e){
            e.printStackTrace();
        }

    }

    public void startChrono(View view) {
        startActivity(new Intent(getBaseContext(), ChronoActivity.class));
    }

    public void startTraining(View view) {
        startActivity(new Intent(getBaseContext(), ScheduleActivity.class));
    }

    public void startExercise(View view) {
        startActivity(new Intent(getBaseContext(), ListeExerciseFragment.class));
    }

    public void startStatistic(View v) {
        startActivity(new Intent(getBaseContext(), StatActivity.class));
    }



}
