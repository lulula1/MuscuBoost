package uqac.dim.muscuboost;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uqac.dim.muscuboost.db.BodyDAO;
import uqac.dim.muscuboost.db.PersonDAO;

public class RegistrationActivity extends AppCompatActivity {

    EditText nom, prenom, dateNaissance, masse, taille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);

        nom = findViewById(R.id.editText_name);
        prenom = findViewById(R.id.editText_surname);
        dateNaissance = findViewById(R.id.editText_date_naissance);
        masse = findViewById(R.id.editText_masse);
        taille = findViewById(R.id.editText_taille);

        Button inscription = findViewById(R.id.button_inscription);
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nom.getText().toString().equals("")
                        || prenom.getText().toString().equals("")
                        || dateNaissance.getText().toString().equals("")
                        || masse.getText().toString().equals("")
                        || taille.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Saisie des données incomplètes", Toast.LENGTH_SHORT).show();
                }
                else {
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date d = new Date();
                    try {
                        d = df.parse(dateNaissance.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    PersonDAO personDao = new PersonDAO(getApplicationContext());
                    personDao.open();
                    personDao.insert(nom.getText().toString(), prenom.getText().toString(), d);
                    personDao.close();

                    BodyDAO bodyDao = new BodyDAO(getApplicationContext());
                    bodyDao.open();
                    bodyDao.insert(Double.parseDouble(masse.getText().toString()), Double.parseDouble(taille.getText().toString()));
                    bodyDao.close();
                    finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed(){
        // Cancel back pressed
    }

}
