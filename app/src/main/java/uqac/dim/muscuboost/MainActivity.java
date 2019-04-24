package uqac.dim.muscuboost;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import uqac.dim.muscuboost.db.DatabaseHandler;
import uqac.dim.muscuboost.db.PersonDAO;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
        //db.dbDelete();
        db.createDatabase();

        PersonDAO personDAO = new PersonDAO(getApplicationContext());
        personDAO.open();
        if(!personDAO.isRegistered()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle(R.string.registration)
                    .setMessage(R.string.registration_message)
                    .setCancelable(false)
                    .setPositiveButton(R.string.signin, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(getBaseContext(), RegistrationActivity.class));
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }

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

    public void startProfil(View v) {
        startActivity(new Intent(getBaseContext(), ProfilActivity.class));
    }

}
