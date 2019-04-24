package uqac.dim.muscuboost;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import java.io.IOException;

import uqac.dim.muscuboost.AjoutExercice.ListeExerciseFragment;
import uqac.dim.muscuboost.db.DatabaseHandler;
import uqac.dim.muscuboost.db.PersonDAO;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        try{
           db.db_delete();
           db.createDatabase();
        }catch(IOException e){
            e.printStackTrace();
        }

        PersonDAO personDAO = new PersonDAO(getApplicationContext());
        personDAO.open();
        if(!personDAO.isRegistered()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Inscription")
                    .setMessage("Inscrivez-vous avant de commencer")
                    .setCancelable(false)
                    .setPositiveButton("S'inscrire", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(getBaseContext(), InscriptionActivity.class));
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
        startActivity(new Intent(getBaseContext(), ListeExerciseFragment.class));
    }

    public void startStatistic(View v) {
        startActivity(new Intent(getBaseContext(), StatActivity.class));
    }

    public void startProfil(View v) {
        startActivity(new Intent(getBaseContext(), ProfilActivity.class));
    }

}
