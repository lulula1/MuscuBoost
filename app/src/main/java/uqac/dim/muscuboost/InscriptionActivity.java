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

public class InscriptionActivity extends AppCompatActivity {

    EditText nom, prenom, dateNaissance, masse, taille;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

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
                    Toast.makeText(getApplicationContext(), "Saisie des données imcoplète", Toast.LENGTH_SHORT).show();
                }
                else {
                    SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                    Date d = new Date();
                    try {
                        d = df.parse(dateNaissance.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    PersonDAO personDAO = new PersonDAO(getApplicationContext());
                    personDAO.open();
                    personDAO.insert(nom.getText().toString(), prenom.getText().toString(), d);
                    personDAO.close();

                    BodyDAO bodyDAO = new BodyDAO(getApplicationContext());
                    bodyDAO.open();
                    bodyDAO.insert(Double.parseDouble(masse.getText().toString()), Double.parseDouble(taille.getText().toString()));
                    bodyDAO.close();
                    finish();
                }
            }
        });

    }

    @Override
    public void onBackPressed(){
        
    }
}
