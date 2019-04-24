package uqac.dim.muscuboost.AjoutExercice;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.db.ExerciseDAO;
import uqac.dim.muscuboost.db.MuscleDAO;

public class EditExerciseActivity extends AppCompatActivity implements OnItemSelectedListener{
    private Button btnImport, btnRemove, btnEdit, btnCancel ;
    private TextView txtTitre;
    private TextView txtDescription;
    private ImageView selectedImg;
    static final int RESULT_LOAD_IMG = 1;
    private ExerciseDAO Exercise_datasource;
    private MuscleDAO Muscle_datasource;
    private String s;
    private Exercise exercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_edit);

        btnImport = findViewById(R.id.import_img);
        btnRemove = findViewById(R.id.remove_img);
        btnEdit = findViewById(R.id.edit_exercice);
        btnCancel = findViewById(R.id.cancel_addExercice);
        selectedImg = findViewById(R.id.selected);
        txtTitre = findViewById(R.id.editTxt_exercise_title);
        txtDescription = findViewById(R.id.editTxt_exercise_description);



        Muscle_datasource = new MuscleDAO(this);
        Muscle_datasource.open();

        Exercise_datasource = new ExerciseDAO(this);
        Exercise_datasource.open();

        Bundle extras = getIntent().getExtras();
        exercise = (Exercise) extras.getSerializable("ExerciceParam");


        txtTitre.setText(exercise.getName());
        txtDescription.setText(exercise.getDescription());

        Spinner spinner = (Spinner) findViewById(R.id.muscles_spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Muscle_datasource.selectAllName());
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);



        /*
        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, RESULT_LOAD_IMG);
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImg.setImageBitmap(null);
                selectedImg.setVisibility(View.GONE);
                btnRemove.setVisibility(View.GONE);
                btnImport.setVisibility(View.VISIBLE);
            }
        });
        */

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if (Exercise_datasource.selectName(txtTitre.getText().toString())!=null) {
                  //  Toast.makeText(getApplicationContext(), "Exercice déjà existant", Toast.LENGTH_LONG).show();
                //}else {
                    exercise.setName(txtTitre.getText().toString());
                    exercise.setDescription(txtDescription.getText().toString());
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("resultFromEditExercise", exercise);
                    setResult(Activity.RESULT_OK, returnIntent);
                    finish();
                //}
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED,returnIntent);
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                selectedImg.setImageBitmap(selectedImage);
                selectedImg.setVisibility(View.VISIBLE);
                btnRemove.setVisibility(View.VISIBLE);
                btnImport.setVisibility(View.GONE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Une erreur s'est produite",Toast.LENGTH_LONG).show();

            }

        }else {
            Toast.makeText(getApplicationContext(),"Vous n'avez pas choisi d'image", Toast.LENGTH_LONG).show();

        }
    }


    @Override
    protected void onResume() {

        Exercise_datasource.open();
        Muscle_datasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {

        Exercise_datasource.close();
        Muscle_datasource.close();
        super.onPause();
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        s=item;
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }
}


