package uqac.dim.muscuboost;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.db.ExerciseDAO;
import uqac.dim.muscuboost.ui.exercise.AddExerciseActivity;
import uqac.dim.muscuboost.ui.exercise.ExerciseDetailsActivity;

public class ExerciseListActivity extends ListActivity {

    private ExerciseDAO exerciseDao;
    private List<Exercise> values;
    private ArrayAdapter<Exercise> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_list_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.exercise_name);

        exerciseDao = new ExerciseDAO(this);
        exerciseDao.open();

        values = exerciseDao.getAll();

        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddExercice = new Intent(getBaseContext(), AddExerciseActivity.class);
                startActivityForResult(intentAddExercice ,1);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        exerciseDao.open();
        values = exerciseDao.getAll();

        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        exerciseDao.close();
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Exercise exercise = (Exercise) getListAdapter().getItem(position);
        Intent intent = new Intent(this, ExerciseDetailsActivity.class);
        intent.putExtra("titre", exercise.getName());
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        exerciseDao = new ExerciseDAO(this);
        exerciseDao.open();

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("resultFromAddExercice");
                Exercise e = exerciseDao.selectName(result);
                adapter.add(e);
                adapter.notifyDataSetChanged();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
            }
        }else if(requestCode == 2) {
            if(resultCode == Activity.RESULT_OK){
                Exercise result= (Exercise) data.getSerializableExtra("resultFromDetailsExercise");
                adapter.remove(result);
                adapter.notifyDataSetChanged();
            }
        }
    }

}