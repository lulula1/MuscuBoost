package uqac.dim.muscuboost.ui.exercise;

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
import uqac.dim.muscuboost.core.training.Muscle;
import uqac.dim.muscuboost.db.ExerciseDAO;
import uqac.dim.muscuboost.db.MuscleDAO;


public class AddExerciseActivity extends AppCompatActivity implements OnItemSelectedListener{

    private Button btnImport, btnRemove, btnAdd, btnCancel ;
    private TextView txtTitre;
    private TextView txtDescription;
    private ImageView selectedImg;
    static final int RESULT_LOAD_IMG = 1;
    private ExerciseDAO exerciseDao;
    private MuscleDAO muscleDao;
    private String s;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercise_fragment);

        btnImport = findViewById(R.id.import_img);
        btnRemove = findViewById(R.id.remove_img);
        btnAdd = findViewById(R.id.edit_exercice);
        btnCancel = findViewById(R.id.cancel_addExercice);
        selectedImg = findViewById(R.id.selected);
        txtTitre = findViewById(R.id.editTxt_exercise_title);
        txtDescription = findViewById(R.id.editTxt_exercise_description);

        muscleDao = new MuscleDAO(this);
        muscleDao.open();

        exerciseDao = new ExerciseDAO(this);
        exerciseDao.open();

        Spinner spinner = findViewById(R.id.muscles_spinner);
        spinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, muscleDao.getAllName());
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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtTitre.getText().toString().trim().matches("")
                  || txtDescription.getText().toString().trim().matches("")) {
                    Toast.makeText(getApplicationContext(), "Veuillez tout remplir", Toast.LENGTH_LONG).show();
                //} else if (exerciseDao.getFromName(txtTitre.getText().toString()) != null){
                }else{
                    Muscle m = muscleDao.getName(s);
                    Exercise exercise = exerciseDao.insert(txtTitre.getText().toString(), m, txtDescription.getText().toString());
                    Intent returnIntent = new Intent();
                    returnIntent.putExtra("resultFromAddExercice",exercise.getName());
                    setResult(Activity.RESULT_OK,returnIntent);
                    finish();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(Activity.RESULT_CANCELED,new Intent());
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
        super.onResume();
        exerciseDao.open();
        muscleDao.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        exerciseDao.close();
        muscleDao.close();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String item = parent.getItemAtPosition(position).toString();
        s=item;
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
    }

}
