package uqac.dim.muscuboost.AjoutExercice;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

import android.widget.AdapterView.OnItemSelectedListener;


public class AddExerciceActivity  extends AppCompatActivity implements OnItemSelectedListener{

    private Button btnImport, btnRemove, btnAdd, btnCancel ;
    private TextView txtTitre;
    private TextView txtDescription;
    private ImageView selectedImg;
    static final int RESULT_LOAD_IMG = 1;
    private ExerciseDAO Exercise_datasource;
    private MuscleDAO Muscle_datasource;
    private String s;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercice_add_fragment);

        btnImport = findViewById(R.id.import_img);
        btnRemove = findViewById(R.id.remove_img);
        btnAdd = findViewById(R.id.add_exercice);
        btnCancel = findViewById(R.id.cancel_addExercice);
        selectedImg = findViewById(R.id.selected);
        txtTitre = findViewById(R.id.editTxt_exercise_title);
        txtDescription = findViewById(R.id.editTxt_exercise_description);

        Muscle_datasource = new MuscleDAO(this);
        Muscle_datasource.open();

        Exercise_datasource = new ExerciseDAO(this);
        Exercise_datasource.open();

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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ListeExerciseFragment l = new ListeExerciseFragment().newInstance();
                ArrayAdapter<Exercise> adapter = (ArrayAdapter<Exercise>) l.getListAdapter();
                Muscle m = Muscle_datasource.selectName(s);
                Exercise exercise = Exercise_datasource.insert(txtTitre.getText().toString(), m, txtDescription.getText().toString());
                adapter.add(exercise);
                adapter.notifyDataSetChanged();
                finish();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
