package uqac.dim.muscuboost;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import java.util.List;

import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.core.training.Training;
import uqac.dim.muscuboost.db.ExerciseDAO;
import uqac.dim.muscuboost.db.TrainingExerciseDAO;
import uqac.dim.muscuboost.ui.trainingshowcase.ExerciseAdapter;

public class TrainingShowcaseActivity extends AppCompatActivity {

    public static final String EXTRA_TRAINING = "training";

    private Training training;

    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.training_showcase_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        training = (Training) getIntent().getSerializableExtra(EXTRA_TRAINING);
        if(training == null) {
            finish();
            return;
        }

        if(getActionBar() != null) {
            getActionBar().setSubtitle(training.getSlotLabel());
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        list = findViewById(R.id.list);

        list.setAdapter(new ExerciseAdapter(getBaseContext(), R.layout.training_exercise_list_item,
                training.getExercises()));

        updateList();
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
        Intent intent = new Intent(getBaseContext(), TrainActivity.class);
        intent.putExtra(TrainActivity.EXTRA_TRAINING, training);
        startActivity(intent);
    }

    public void addExercise(View view) {
        ExerciseDAO exerciseDAO = new ExerciseDAO(this);
        exerciseDAO.open();

        final List<Exercise> exercises = exerciseDAO.getAll();
        exercises.removeAll(training.getExercises());

        exerciseDAO.close();

        DialogInterface.OnClickListener onSubmit = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                TrainingExerciseDAO teDAO = new TrainingExerciseDAO(TrainingShowcaseActivity.this);
                AlertDialog dialog = (AlertDialog) dialogInterface;
                teDAO.open();

                SparseBooleanArray checkedItems = dialog.getListView().getCheckedItemPositions();
                for(int i = 0; i < checkedItems.size(); i++) {
                    Exercise checkedExercise = (Exercise) dialog.getListView()
                            .getItemAtPosition(checkedItems.keyAt(i));
                    teDAO.insert(training.getId(), checkedExercise.getId());
                    training.addExercise(checkedExercise);
                }

                teDAO.close();
                updateList();
            }
        };

        final AlertDialog dialog = new AlertDialog.Builder(getBaseContext())
                .setAdapter(new ExerciseAdapter(getBaseContext(),
                        R.layout.training_exercise_dialog_list_item, exercises), null)
                .setTitle(getString(R.string.new_exercises))
                .setMessage(exercises.isEmpty() ? getString(R.string.no_exercise_available) : null)
                .setPositiveButton(getString(R.string.add), onSubmit)
                .setNegativeButton(getString(R.string.cancel), null)
                .create();

        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface arg0) {
                Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positive.setTextColor(getResources().getColor(R.color.colorPrimary));
                positive.setEnabled(false);

                Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                negative.setTextColor(getResources().getColor(R.color.colorPrimary));
            }
        });
        dialog.getListView().setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        dialog.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int checkCount = dialog.getListView().getCheckedItemCount();
                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setEnabled(checkCount > 0);
                ((CheckBox) view.findViewById(R.id.checkbox)).toggle();
            }
        });
        dialog.show();
    }

    public void updateList() {
        ExerciseAdapter adapter = (ExerciseAdapter) list.getAdapter();
        boolean empty = adapter.getCount() <= 0;

        findViewById(R.id.start).setEnabled(!empty);
        findViewById(R.id.empty_list).setVisibility(empty ? View.VISIBLE : View.GONE);
        adapter.notifyDataSetChanged();
    }

}
