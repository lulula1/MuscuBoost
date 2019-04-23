package uqac.dim.muscuboost.AjoutExercice;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.util.List;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.db.ExerciseDAO;



public class ListeExerciseFragment extends ListActivity {

    private ExerciseDAO Exercisedatasource;
    private Toolbar toolbar;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercice_main_activity);

        toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("Liste des exercices");


        Exercisedatasource = new ExerciseDAO(this);
        Exercisedatasource.open();

        List<Exercise> values = Exercisedatasource.getAll();

        ArrayAdapter<Exercise> adapter = new ArrayAdapter<Exercise>(
                this, android.R.layout.simple_list_item_1, values);
        setListAdapter(adapter);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddExerciceActivity.class));
            }
        });

    }

    public static ListeExerciseFragment newInstance() {
        ListeExerciseFragment fragment = new ListeExerciseFragment();
        return fragment;
    }



    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Exercise exercice = (Exercise) getListAdapter().getItem(position);
        Intent intent = new Intent(this, DetailExerciseActivity.class);
        intent.putExtra("titre",  exercice.getName());
        startActivity(intent);

    }

    @Override
    protected void onResume() {

        Exercisedatasource.open();
        super.onResume();
    }

    @Override
    protected void onPause() {

        Exercisedatasource.close();
        super.onPause();
    }

}