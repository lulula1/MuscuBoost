package uqac.dim.muscuboost;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import uqac.dim.muscuboost.graph.ListOptionGraph;

public class StatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.fragment_list_container, ListOptionGraph.newInstance(ListOptionGraph.TYPE_MUSCLE))
                    .addToBackStack(null).commit();
        }


        Button parMuscle = findViewById(R.id.button_par_muscle);
        parMuscle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragment_list_container,ListOptionGraph.newInstance(ListOptionGraph.TYPE_MUSCLE))
                        .addToBackStack(null).commit();

            }
        });

        Button parExercice = findViewById(R.id.button_par_exercice);
        parExercice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragment_list_container,ListOptionGraph.newInstance(ListOptionGraph.TYPE_EXERCICE))
                        .addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
