package uqac.dim.muscuboost;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import uqac.dim.muscuboost.core.training.Training;
import uqac.dim.muscuboost.ui.training.ExerciseAdapter;

public class TrainingShowcaseActivity extends ListActivity {

    public static final String EXTRA_TRAINING = "training";

    private Training training;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_showcase_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        if(getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);

        training = (Training) getIntent().getSerializableExtra(EXTRA_TRAINING);
        setTitle(training.getSlotLabel());

        setListAdapter(new ExerciseAdapter(getBaseContext(), training.getExercises()));

        if(getListAdapter().getCount() > 0)
            findViewById(R.id.start).setEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();

        TextView trainingName = findViewById(R.id.name);
        trainingName.setText(training.getName());


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void startTraining(View view) {
        // TODO
    }

}
