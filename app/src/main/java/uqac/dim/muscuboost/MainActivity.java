package uqac.dim.muscuboost;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.core.training.Muscle;
import uqac.dim.muscuboost.db.ExerciseDAO;
import uqac.dim.muscuboost.db.MuscleDAO;

public class MainActivity extends AppCompatActivity {

    MuscleDAO md = new MuscleDAO(this);
    ExerciseDAO ed = new ExerciseDAO(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        affichage();

        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edt = findViewById(R.id.editText);
                Muscle m = new Muscle(edt.getText().toString());
                md.open();
                md.insert(m);
                affichage();
            }
        });

    }

    public void affichage(){
        LinearLayout l = findViewById(R.id.liste);
        l.removeAllViews();
        md.open();

        List<Muscle> muscles = md.select("", null);
        for(Muscle m : muscles){
            TextView t = new TextView(this);
            t.setText(m.getId() + " --> " + m.getName());
            t.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            t.setTextSize(18);
            t.setTextColor(Color.BLACK);
            l.addView(t);
        }

        l = findViewById(R.id.liste_exos);
        l.removeAllViews();
        ed.open();
        List<Exercise> exos = ed.select("", null);
        for(Exercise e : exos){
            TextView t = new TextView(this);
            t.setText(e.getId() + " --> " + e.getName() + " -- " + e.getMuscle().getName());
            t.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            t.setTextSize(18);
            t.setTextColor(Color.BLACK);
            l.addView(t);
        }

    }
}
