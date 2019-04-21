package uqac.dim.muscuboost;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddExerciseActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_LOAD_IMG = 1;

    private Button btnImport;
    private Button btnRemove;
    private ImageView selectedImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_exercice_fragment);
        if(getActionBar() != null)
            getActionBar().setHomeButtonEnabled(true);

        btnImport = findViewById(R.id.import_img);
        btnRemove = findViewById(R.id.remove_img);
        selectedImg = findViewById(R.id.selected);

        btnImport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, REQUEST_CODE_LOAD_IMG);
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOAD_IMG && resultCode == RESULT_OK) {
            try {
                Uri imageUri = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                selectedImg.setImageBitmap(selectedImage);
                selectedImg.setVisibility(View.VISIBLE);
                btnRemove.setVisibility(View.VISIBLE);
                btnImport.setVisibility(View.GONE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), R.string.error_occurred, Toast.LENGTH_LONG).show();
            }
        }
    }

    // TODO - Implement exercise addition

}
