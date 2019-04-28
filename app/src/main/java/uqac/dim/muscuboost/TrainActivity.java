package uqac.dim.muscuboost;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

import uqac.dim.muscuboost.core.ongoingtraining.Observer;
import uqac.dim.muscuboost.core.ongoingtraining.OngoingTraining;
import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.core.training.Statistics;
import uqac.dim.muscuboost.core.training.Training;
import uqac.dim.muscuboost.db.StatisticsDAO;
import uqac.dim.muscuboost.ui.dialog.ConfirmDialog;
import uqac.dim.muscuboost.ui.train.StatRecapView;

public class TrainActivity extends AppCompatActivity implements ServiceConnection, Observer {

    public static String EXTRA_TRAINING = "uqac.dim.muscuboost.TrainActivity.EXTRA_TRAINING";

    private OngoingTraining ongoingTraining;

    private TrainService service;
    private boolean serviceBound = false;

    private StatisticsDAO statisticsDAO = new StatisticsDAO(this);

    private LinearLayout trainingPanel;
    private LinearLayout endPanel;
    private LinearLayout statRecap;
    private TextView trainingNameView;
    private TextView exerciseNameView;
    private TextView muscleNameView;
    private TextView exerciseCountView;
    private TextView seriesCountView;
    private EditText weightEdit;
    private TextView latestWeightView;
    private TextView latestSeriesCountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_activity);

        statisticsDAO.open();

        Training training = (Training) getIntent().getSerializableExtra(EXTRA_TRAINING);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = new Intent(getBaseContext(), TrainService.class);
        if(training != null)
            intent.putExtra(TrainService.EXTRA_TRAINING, training);
        startService(intent);
        getApplicationContext().bindService(intent, this, BIND_AUTO_CREATE);

        trainingPanel = findViewById(R.id.training_panel);
        endPanel = findViewById(R.id.end_panel);
        statRecap = findViewById(R.id.exercise_recap);
        trainingNameView = findViewById(R.id.training_name);
        exerciseNameView = findViewById(R.id.exercise_name);
        muscleNameView = findViewById(R.id.muscle_name);
        exerciseCountView = findViewById(R.id.exercise_count);
        seriesCountView = findViewById(R.id.series_count);
        weightEdit = findViewById(R.id.weight);
        latestWeightView = findViewById(R.id.latest_weight);
        latestSeriesCountView = findViewById(R.id.latest_series_count);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.train_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.stop:
                new ConfirmDialog(this)
                        .setTitle(R.string.stop_training_confirm)
                        .setPositiveListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                stopTraining();
                            }
                        }).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setTrainingName() {
        String trainingName = ongoingTraining.getTraining().getName();
        if(getSupportActionBar() != null)
            getSupportActionBar().setSubtitle(trainingName);
        trainingNameView.setText(trainingName);
    }

    @Override
    public void onUpdate() {
        if(!ongoingTraining.isTrainingOver()) {
            updateExercise();
            updateSeries();
            if(ongoingTraining.getSeries() <= 1)
                setupStats();
        }else {
            setTrainingOverPanel();
        }
    }

    private void updateExercise() {
        Exercise exercise = ongoingTraining.getCurrentExercise();
        if(exercise != null) {
            exerciseNameView.setText(exercise.getName());
            muscleNameView.setText(exercise.getMuscle().getName());
            exerciseCountView.setText(getString(R.string.exercise) + " "
                            + (ongoingTraining.getDoneExercisesCount() + 1)
                            + "/" + ongoingTraining.getExerciseCount());
        }
    }

    private void updateSeries() {
        int series = ongoingTraining.getSeries();
        seriesCountView.setText(getString(R.string.series) + " #" + series);
    }

    private void setupStats() {
        long exerciseId = ongoingTraining.getCurrentExercise().getId();
        Statistics latestStats = statisticsDAO.getLatestStatistics(exerciseId);

        if(latestStats != null) {
            weightEdit.setHint(String.valueOf(latestStats.getWeight()));
            latestWeightView.setText(String.format(getString(R.string.latest_weight),
                    String.valueOf(latestStats.getWeight())));
            latestSeriesCountView.setText(String.format(getString(R.string.latest_series_count),
                    String.valueOf(latestStats.getRepCount())));
        }else {
            weightEdit.setHint("n/a");
            latestWeightView.setVisibility(View.GONE);
            latestSeriesCountView.setVisibility(View.GONE);
        }
    }

    private void setTrainingOverPanel() {
        trainingPanel.setVisibility(View.GONE);
        endPanel.setVisibility(View.VISIBLE);

        statRecap.removeAllViews();

        Map<Exercise, Statistics> exerciseStats = service
                .getOngoingTraining().getDoneExerciseStats();
        for(Map.Entry<Exercise, Statistics> entry : exerciseStats.entrySet())
            statRecap.addView(
                    new StatRecapView(getBaseContext(), entry.getKey(), entry.getValue(),
                            statisticsDAO.getLatestStatistics(entry.getKey().getId())));
    }

    public void nextExercise(View view) {
        try {
            ongoingTraining.setWeight(
                    Double.parseDouble(weightEdit.getText().toString()));
        }catch(NumberFormatException e) {
            // The value is not a double, don't do anything
        }
        weightEdit.getText().clear();
        service.nextExercise();
    }

    public void nextSeries(View view) {
        service.nextSeries();
    }

    public void saveQuitTraining(View view) {
        service.saveStats();
        stopTraining();
    }

    public void stopTraining() {
        stopService(new Intent(getBaseContext(), TrainService.class));
        finish();
    }

}
