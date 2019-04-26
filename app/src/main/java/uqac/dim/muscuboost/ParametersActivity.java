package uqac.dim.muscuboost;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import uqac.dim.muscuboost.db.DatabaseHandler;
import uqac.dim.muscuboost.ui.dialog.ConfirmDialog;

public class ParametersActivity extends AppCompatActivity {

    Button reinit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parameters_activity);

        reinit = findViewById(R.id.button_reinit);
        reinit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ConfirmDialog(ParametersActivity.this)
                        .setTitle(R.string.erase_data)
                        .setMessage(R.string.erase_data_message)
                        .setPositiveListener(new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                                db.dbDelete();
                                db.createDatabase();

                                Intent returnIntent = new Intent();
                                setResult(Activity.RESULT_OK, returnIntent);
                                finish();
                            }
                        }).show();
            }
        });
    }
}
