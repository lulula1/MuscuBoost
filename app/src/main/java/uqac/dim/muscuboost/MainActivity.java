package uqac.dim.muscuboost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import uqac.dim.muscuboost.db.DatabaseHandler;
import uqac.dim.muscuboost.db.PersonDAO;


public class MainActivity extends AppCompatActivity {

    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        DatabaseHandler db = new DatabaseHandler(getApplicationContext());

        //db.dbDelete();
        db.createDatabase();

        inscription();
    }

    private void inscription(){

        PersonDAO personDAO = new PersonDAO(getApplicationContext());
        personDAO.open();
        if(!personDAO.isRegistered()){
            AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Inscription")
                    .setMessage("Inscrivez-vous avant de commencer")
                    .setCancelable(false)
                    .setPositiveButton("S'inscrire", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivityForResult(new Intent(getBaseContext() , RegistrationActivity.class),1);
                        }
                    });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();

        }
        personDAO.close();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == 1){
            if(resultCode == Activity.RESULT_OK){
                name = data.getStringExtra("surname");
            }
        } else if(requestCode == 2){
            if(resultCode == Activity.RESULT_OK) {
                Log.i("DIM", "JE PASSE PAR INSCRIPTION");
                inscription();
            }
        }
    }

    @Override
    public void onResume() {
        chargerBonjour();
        super.onResume();
    }

    private void chargerBonjour() {
        TextView bonjour = findViewById(R.id.welcome);
        bonjour.setVisibility(name == null ? View.GONE : View.VISIBLE);
        bonjour.setText(getString(R.string.hello) + " " + name);
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

    public void startParameters(View v){
        startActivityForResult(new Intent(getBaseContext(), ParametersActivity.class),2);
    }

}
