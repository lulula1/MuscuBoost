package uqac.dim.muscuboost.AjoutExercice;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.List;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.db.ExerciseDAO;



public class ListeExerciseFragment extends ListActivity {

    private ExerciseDAO Exercisedatasource;
    private Toolbar toolbar;
    private List<Exercise> values;
    private ArrayAdapter<Exercise> adapter;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercice_main_activity);

        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Liste des exercices");


        Exercisedatasource = new ExerciseDAO(this);
        Exercisedatasource.open();

        values = Exercisedatasource.getAll();

        adapter = new ArrayAdapter<Exercise>(
                this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAddExercice = new Intent(getBaseContext(), AddExerciceActivity.class);
                startActivityForResult(intentAddExercice ,1);
            }
        });

    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Exercise exercice = (Exercise) getListAdapter().getItem(position);
        Intent intentDetailExercise = new Intent(this, DetailExerciseActivity.class);
        intentDetailExercise.putExtra("titre",  exercice.getName());
        startActivityForResult(intentDetailExercise ,2);
    }

    @Override
    protected void onResume() {
        Exercisedatasource.open();
        values = Exercisedatasource.getAll();

        adapter = new ArrayAdapter<Exercise>(
                this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);
        super.onResume();
    }

    @Override
    protected void onPause() {

        Exercisedatasource.close();
        super.onPause();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Exercisedatasource = new ExerciseDAO(this);
        Exercisedatasource.open();

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("resultFromAddExercice");
                Exercise e = Exercisedatasource.selectName(result);
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