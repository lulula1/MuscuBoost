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
import android.widget.ImageButton;
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

    private ExerciseDAO exerciseDao = new ExerciseDAO(this);
    private TrainingExerciseDAO teDao = new TrainingExerciseDAO(this);

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
        list.setEnabled(false);

        updateList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        TextView trainingName = findViewById(R.id.name);
        trainingName.setText(training.getName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        exerciseDao.open();
        teDao.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        exerciseDao.close();
        teDao.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater()
                .inflate(R.menu.training_showcase_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.edit:
                for(int i = 0; i < list.getChildCount(); i++) {
                    ImageButton editBtn = list.getChildAt(i).findViewById(R.id.edit);
                    editBtn.setVisibility(editBtn.getVisibility() == View.VISIBLE
                            ? View.GONE
                            : View.VISIBLE);
                }
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
        final List<Exercise> exercises = exerciseDao.getAll();
        exercises.removeAll(training.getExercises());

        DialogInterface.OnClickListener onSubmit = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                AlertDialog dialog = (AlertDialog) dialogInterface;

                SparseBooleanArray checkedItems = dialog.getListView().getCheckedItemPositions();
                for(int i = 0; i < checkedItems.size(); i++) {
                    Exercise checkedExercise = (Exercise) dialog.getListView()
                            .getItemAtPosition(checkedItems.keyAt(i));
                    teDao.insert(training.getId(), checkedExercise.getId());
                    training.addExercise(checkedExercise);
                }

                updateList();
            }
        };

        final AlertDialog dialog = new AlertDialog.Builder(this)
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

    public void removeExercise(View view) {
        int position = list.getPositionForView((View) view.getParent());
        if(position >= 0) {
            Exercise exercise = (Exercise) list.getItemAtPosition(position);
            teDao.delete(training.getId(), exercise.getId());
            training.removeExercise(exercise);
            updateList();
        }
    }

    public void updateList() {
        ExerciseAdapter adapter = (ExerciseAdapter) list.getAdapter();
        boolean empty = adapter.getCount() <= 0;

        findViewById(R.id.start).setEnabled(!empty);
        findViewById(R.id.empty_list).setVisibility(empty ? View.VISIBLE : View.GONE);
        list.setVisibility(empty ? View.GONE : View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

}
