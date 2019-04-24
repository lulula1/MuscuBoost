package uqac.dim.muscuboost;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import uqac.dim.muscuboost.db.BodyDAO;
import uqac.dim.muscuboost.db.PersonDAO;

public class RegistrationActivity extends AppCompatActivity {

    private Button date;
    private EditText nom, prenom, masse, taille;
    TextView dateNaissance;

    private int jour, mois, annee;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        nom = findViewById(R.id.editText_name);
        prenom = findViewById(R.id.editText_surname);
        masse = findViewById(R.id.editText_masse);
        taille = findViewById(R.id.editText_taille);
        date = findViewById(R.id.button_date_naissance);
        dateNaissance = findViewById(R.id.tv_dateNaissance);

        dateNaissance.setVisibility(View.GONE);

        Calendar cal = Calendar.getInstance();

        jour = cal.get(Calendar.DAY_OF_MONTH);
        mois = cal.get(Calendar.MONTH);
        annee = cal.get(Calendar.YEAR);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("DIM", " clicker");

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegistrationActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        annee, mois, jour);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                dateNaissance.setVisibility(View.VISIBLE);
                dateNaissance.setText(String.format("%02d", dayOfMonth)+ "/"+String.format("%02d", month)+"/"+year);

                date.setText("Modifier");
                date.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));

                jour = dayOfMonth;
                mois = month;
                annee = year;
            }
        };

        Button inscription = findViewById(R.id.button_inscription);
        inscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(nom.getText().toString().equals("")
                        || prenom.getText().toString().equals("")
                        || masse.getText().toString().equals("")
                        || taille.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Saisie des données imcoplète", Toast.LENGTH_SHORT).show();
                }
                else {

                    Calendar calendar = Calendar.getInstance();
                    calendar.set(annee, mois-1,jour);



                    PersonDAO personDAO = new PersonDAO(getApplicationContext());
                    personDAO.open();
                    personDAO.insert(nom.getText().toString(), prenom.getText().toString(), calendar.getTime());
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
    public void onBackPressed() {
    }
}
