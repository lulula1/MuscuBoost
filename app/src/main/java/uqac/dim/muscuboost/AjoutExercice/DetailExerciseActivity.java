package uqac.dim.muscuboost.AjoutExercice;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.db.ExerciseDAO;

public class DetailExerciseActivity extends Activity {

    private ExerciseDAO Exercisedatasource;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_exercice_activite);

        Exercisedatasource = new ExerciseDAO(this);
        Exercisedatasource.open();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String titre = extras.getString("titre");
            Exercise exercise = Exercisedatasource.selectName(titre);
            if (exercise != null){
                // option #1 : Directement par le parent
                setExercice(exercise);

                // option #2 : Utiliser à nouveau le fragment
                DetailsExerciseFragment fragment = (DetailsExerciseFragment) getFragmentManager()
                        .findFragmentById(R.id.detailsExerciceFragment);
                fragment.setExercice(exercise);
            }

        }
    }

    public void setExercice(Exercise exercise) {
        ((TextView) findViewById(R.id.titre)).setText(exercise.getName());
        ((TextView) findViewById(R.id.txtView_description)).setText(exercise.getDescription());
        ((TextView) findViewById(R.id.txtView_muscle)).setText(exercise.getMuscle().getName());
    }
}
