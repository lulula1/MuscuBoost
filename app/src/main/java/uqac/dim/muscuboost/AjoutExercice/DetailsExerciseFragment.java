package uqac.dim.muscuboost.AjoutExercice;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.db.ExerciseDAO;



public class DetailsExerciseFragment extends Fragment {

    private ExerciseDAO Exercisedatasource;
    private Exercise exercise;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        Exercisedatasource = new ExerciseDAO(getActivity());
        Exercisedatasource.open();
     }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.details_exercice, container, false);
        return v;
    }

    public void setExercice(Exercise exercise) {
        this.exercise = exercise;
        ((TextView) getView().findViewById(R.id.titre)).setText(exercise.getName());
        ((TextView) getView().findViewById(R.id.txtView_description)).setText(exercise.getDescription());
        ((TextView) getView().findViewById(R.id.txtView_muscle)).setText(exercise.getMuscle().getName());
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail_exercice, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_modifier:
                Toast.makeText(getActivity(), "modifier", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_supprimer:
                ListeExerciseFragment l = new ListeExerciseFragment().newInstance();
                ArrayAdapter<Exercise> adapter = (ArrayAdapter<Exercise>) l.getListAdapter();
                exercise = Exercisedatasource.selectName(((TextView) getView().findViewById(R.id.titre)).getText().toString());
                Exercisedatasource.deleteName(exercise.getName());
                if(adapter!= null){
                    adapter.remove(exercise);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getActivity(), "casse la tete",Toast.LENGTH_LONG).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}


