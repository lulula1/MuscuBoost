package uqac.dim.muscuboost;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import uqac.dim.muscuboost.core.ongoingtraining.Observer;
import uqac.dim.muscuboost.core.ongoingtraining.OngoingTraining;
import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.core.training.Training;

public class TrainActivity extends AppCompatActivity implements ServiceConnection, Observer {

    public static String EXTRA_TRAINING = "uqac.dim.muscuboost.TrainActivity.EXTRA_TRAINING";

    private OngoingTraining ongoingTraining;

    private TrainService service;
    private boolean serviceBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_activity);

        Training training = (Training) getIntent().getSerializableExtra(EXTRA_TRAINING);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.baseline_close_white_24);
        }

        Intent intent = new Intent(getBaseContext(), TrainService.class);
        if(training != null)
            intent.putExtra(TrainService.EXTRA_TRAINING, training);
        startService(intent);
        getApplicationContext().bindService(intent, this, BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(serviceBound)
            getApplicationContext().unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        this.service = ((TrainService.TrainBinder) service).getService();
        this.service.register(this);
        ongoingTraining = this.service.getOngoingTraining();
        serviceBound = true;
        setTrainingName();
        onUpdate();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        service.unregister(this);
        service = null;
        serviceBound = false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // TODO - Confirm closing/training ending
                stopTraining();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setTrainingName() {
        String trainingName = ongoingTraining.getTraining().getName();
        if(getSupportActionBar() != null)
            getSupportActionBar().setSubtitle(trainingName);
        ((TextView) findViewById(R.id.training_name))
                .setText(trainingName);
    }

    @Override
    public void onUpdate() {
        if(!ongoingTraining.isTrainingOver()) {
            updateExercise();
            updateSeries();
        }else {
            setTrainingOverPanel(true);
        }
    }

    private void updateExercise() {
        Exercise exercise = ongoingTraining.getCurrentExercise();
        if(exercise != null) {
            ((TextView) findViewById(R.id.exercise_name))
                    .setText(exercise.getName());
            ((TextView) findViewById(R.id.muscle_name))
                    .setText(exercise.getMuscle().getName());
            ((TextView) findViewById(R.id.exercise_count))
                    .setText(getString(R.string.exercise) + " "
                            + (ongoingTraining.getDoneExercisesCount() + 1)
                            + "/" + ongoingTraining.getExerciseCount());
        }
    }

    private void updateSeries() {
        int series = ongoingTraining.getSeries();
        ((TextView) findViewById(R.id.series_count))
                .setText(getString(R.string.series) + " #" + series);
    }

    private void setTrainingOverPanel(boolean visible) {
        findViewById(R.id.end_panel).setVisibility(visible ? View.VISIBLE : View.GONE);
        findViewById(R.id.training_panel).setVisibility(!visible ? View.VISIBLE : View.GONE);
    }

    public void nextExercise(View view) {
        service.nextExercise();
    }

    public void nextSeries(View view) {
        service.nextSeries();
    }

    public void stopTraining() {
        stopService(new Intent(getBaseContext(), TrainService.class));
        finish();
    }

}
