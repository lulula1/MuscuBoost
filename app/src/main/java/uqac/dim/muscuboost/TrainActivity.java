package uqac.dim.muscuboost;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import uqac.dim.muscuboost.core.training.Training;

public class TrainActivity extends AppCompatActivity implements ServiceConnection {

    public static String EXTRA_TRAINING = "training";

    private Training training;

    private TrainService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_activity);

        training = (Training) getIntent().getSerializableExtra(EXTRA_TRAINING);
        if(training != null) {
            Intent intent = new Intent(getBaseContext(), TrainService.class);
            intent.putExtra(TrainService.EXTRA_TRAINING, training);
            startService(intent);
            bindService(intent, this, BIND_AUTO_CREATE);
        }else
            finish();
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

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        this.service = ((TrainService.TrainBinder) service).getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        this.service = null;
    }

    public void stopTraining(View view) {
        stopService(new Intent(getBaseContext(), TrainService.class));
        finish();
    }

}
