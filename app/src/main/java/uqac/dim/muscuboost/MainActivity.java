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

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MuscleDAO md = new MuscleDAO(this);
    ExerciceDAO ed = new ExerciceDAO(this);


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
                md.ajouter(m);
                affichage();
            }
        });

    }

    public void affichage(){
        LinearLayout l = findViewById(R.id.liste);
        l.removeAllViews();
        md.open();

        ArrayList<Muscle> muscles = md.selectionner("", null);
        for(Muscle m : muscles){
            TextView t = new TextView(this);
            t.setText(m.getId() + " --> " + m.getIntitule());
            t.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            t.setTextSize(18);
            t.setTextColor(Color.BLACK);
            l.addView(t);
        }

        l = findViewById(R.id.liste_exos);
        l.removeAllViews();
        ed.open();
        ArrayList<Exercice> exos = ed.selectionner("", null);
        for(Exercice e : exos){
            TextView t = new TextView(this);
            t.setText(e.getId() + " --> " + e.getIntitule() + " -- " + e.getMuscle().getIntitule());
            t.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            t.setTextSize(18);
            t.setTextColor(Color.BLACK);
            l.addView(t);
        }

    }
}
