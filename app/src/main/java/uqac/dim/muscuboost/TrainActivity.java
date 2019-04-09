package uqac.dim.muscuboost;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uqac.dim.muscuboost.core.training.Training;

public class TrainActivity extends AppCompatActivity {

    public static String EXTRA_TRAINING = "training";

    private Training training;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_activity);

        training = (Training) getIntent().getSerializableExtra(EXTRA_TRAINING);
        if(training == null) {
            finish();
            return;
        }

        startService();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("training", training);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        training = (Training) savedInstanceState.getSerializable("training");
    }

    void startService() {
        Intent intent = new Intent(getBaseContext(), TrainService.class);
        intent.putExtra(TrainService.EXTRA_TRAINING, training);
        startService(intent);
    }

    void stopService() {
        stopService(new Intent(getBaseContext(), TrainService.class));
    }

}
