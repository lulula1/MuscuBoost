package uqac.dim.muscuboost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import uqac.dim.muscuboost.db.DatabaseHandler;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        //db.dbDelete();
        db.createDatabase();

    }

    public void startChrono(View view) {
        startActivity(new Intent(getBaseContext(), ChronoActivity.class));
    }

    public void startTraining(View view) {
        startActivity(new Intent(getBaseContext(), ScheduleActivity.class));
    }

    public void startExercise(View view) {
        startActivity(new Intent(getBaseContext(), ExerciseListActivity.class));
    }

    public void startStatistic(View v) {
        startActivity(new Intent(getBaseContext(), StatActivity.class));
    }

}
