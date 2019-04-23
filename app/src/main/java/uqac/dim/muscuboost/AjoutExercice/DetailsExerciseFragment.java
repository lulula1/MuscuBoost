package uqac.dim.muscuboost.AjoutExercice;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.db.ExerciseDAO;



public class DetailsExerciseFragment extends Fragment {

    private ExerciseDAO Exercisedatasource;

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
                Exercise exercise = Exercisedatasource.selectName(((TextView) getView().findViewById(R.id.titre)).getText().toString());
                Intent intentEditExercice = new Intent(getActivity(), EditExerciseActivity.class);
                intentEditExercice.putExtra("ExerciceParam",exercise);
                startActivityForResult(intentEditExercice ,3);
                return true;
            case R.id.action_supprimer:
                Exercise exercise1 = Exercisedatasource.selectName(((TextView) getView().findViewById(R.id.titre)).getText().toString());
                Exercisedatasource.delete(exercise1);
                Intent returnIntent = new Intent();
                returnIntent.putExtra("resultFromDetailsExercise",exercise1);
                getActivity().setResult(Activity.RESULT_OK,returnIntent);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Exercisedatasource = new ExerciseDAO(getActivity());
        Exercisedatasource.open();

        if (requestCode == 3) {
            if(resultCode == Activity.RESULT_OK){
                Exercise result =(Exercise) data.getSerializableExtra("resultFromEditExercise");
                setExercice(result);
                Exercisedatasource.update(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
            }
        }
    }

}


