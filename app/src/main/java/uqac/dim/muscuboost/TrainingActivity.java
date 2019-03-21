package uqac.dim.muscuboost;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toolbar;

import uqac.dim.muscuboost.core.training.Training;

public class TrainingActivity extends ListActivity {

    public static final String EXTRA_TRAINING = "training";

    private Training training;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);
        if(getActionBar() != null)
            getActionBar().setDisplayHomeAsUpEnabled(true);

        training = (Training) getIntent().getSerializableExtra(EXTRA_TRAINING);
        setTitle(training.getSlotLabel());
    }

    @Override
    protected void onStart() {
        super.onStart();

        ((TextView) findViewById(R.id.name)).setText(training.getName());
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

}
