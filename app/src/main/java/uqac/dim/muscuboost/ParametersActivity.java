package uqac.dim.muscuboost;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import uqac.dim.muscuboost.db.DatabaseHandler;

public class ParametersActivity extends AppCompatActivity {

    Button reinit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parameters);

        reinit = findViewById(R.id.button_reinit);
        reinit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ParametersActivity.this)
                        .setTitle("Suppression des données")
                        .setMessage("Cela entrainera la suppression de l'ensemble de vos données...\nEn êtes vous certain ?")
                        .setCancelable(true)
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                db.dbDelete();
                                db.createDatabase();

                                Intent returnIntent = new Intent();
                                setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
}
