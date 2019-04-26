package uqac.dim.muscuboost.ui.exercise;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.db.ExerciseDAO;

public class ExerciseDetailsActivity extends AppCompatActivity {

    private ExerciseDAO exerciseDao;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_details_activity);

        exerciseDao = new ExerciseDAO(this);
        exerciseDao.open();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String titre = extras.getString("titre");
            Exercise exercise = exerciseDao.getFromName(titre);
            if (exercise != null){
                // option #1 : Directement par le parent
                setExercice(exercise);

                // option #2 : Utiliser à nouveau le fragment
                ExerciseDetailsFragment fragment = (ExerciseDetailsFragment) getFragmentManager()
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
