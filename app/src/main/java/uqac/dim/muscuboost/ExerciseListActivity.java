package uqac.dim.muscuboost;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.db.ExerciseDAO;
import uqac.dim.muscuboost.ui.exercise.AddExerciseActivity;
import uqac.dim.muscuboost.ui.exercise.ExerciseDetailsActivity;

public class ExerciseListActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    private ListView list;

    private ExerciseDAO exerciseDao;
    private List<Exercise> values;
    private ArrayAdapter<Exercise> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_list_activity);

        exerciseDao = new ExerciseDAO(this);
        exerciseDao.open();

        list = findViewById(R.id.list);

        values = exerciseDao.getAll();

        adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, values);
        list.setAdapter(adapter);
        list.setOnItemClickListener(this);

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
        list.setAdapter(adapter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        exerciseDao.close();
    }

    @Override
    public void onItemClick(AdapterView listView, View view, int position, long id) {
        Exercise exercise = (Exercise) list.getAdapter().getItem(position);
        Intent intent = new Intent(this, ExerciseDetailsActivity.class);
        intent.putExtra("titre", exercise.getName());
        startActivity(intent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        exerciseDao = new ExerciseDAO(this);
        exerciseDao.open();

        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == 1) {
                String result=data.getStringExtra("resultFromAddExercice");
                Exercise e = exerciseDao.getFromName(result);
                adapter.add(e);
                adapter.notifyDataSetChanged();
            }else if(requestCode == 2) {
                Exercise result= (Exercise) data.getSerializableExtra("resultFromDetailsExercise");
                adapter.remove(result);
                adapter.notifyDataSetChanged();
            }
        }
    }

}