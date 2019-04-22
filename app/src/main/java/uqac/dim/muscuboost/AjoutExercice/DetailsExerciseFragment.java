package uqac.dim.muscuboost.AjoutExercice;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.db.ExerciseDAO;



public class DetailsExerciseFragment extends Fragment {

    private ExerciseDAO Exercisedatasource;
    private Exercise exercise;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Exercisedatasource = new ExerciseDAO(getActivity());
        Exercisedatasource.open();

     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.details_exercice, container, false);
        setHasOptionsMenu(true);
        return v;
    }

    public void setExercice(Exercise exercise) {
        this.exercise = exercise;
        ((TextView) getView().findViewById(R.id.titre)).setText(exercise.getName());
        ((TextView) getView().findViewById(R.id.txtView_description)).setText(exercise.getDescription());
        ((TextView) getView().findViewById(R.id.txtView_muscle)).setText(exercise.getMuscle().getName());
    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_detail_exercice, menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.action_modifier)
        {

        }else if(itemId == R.id.action_supprimer)
        {

        }
        return super.onOptionsItemSelected(item);
    }


}


