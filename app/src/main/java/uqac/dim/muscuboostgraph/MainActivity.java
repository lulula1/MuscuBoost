package uqac.dim.muscuboostgraph;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import uqac.dim.muscuboostgraph.graph.ListOptionGraph;


public class MainActivity extends AppCompatActivity {


//    StatisticsDAO sd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
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

//        DatabaseHandler db = new DatabaseHandler(this);
//        try {
//            Log.i("DIM", "CREATION DATABASE  ");
//            db.db_delete();
//            db.createDatabase();
//
//        } catch (IOException ioe) {
//
//            throw new Error("Unable to create database");
//        }
//
//        try {
//            db.openDatabase();
//        }catch(SQLException sqle){
//            throw sqle;
//        }
//
//        sd = new StatisticsDAO(getApplicationContext());
//        affichage();

    }

//    public void affichage(){
//
//        sd.open();
//        String s = "id IN (SELECT stat_id FROM stat_exercise WHERE exercise_id = 2)";
//        String[] whereArgs = {};
//        Log.i("DIM",""+sd.getDb());
//        List<Statistics> stats= sd.select(s, whereArgs);
//
//
//        GraphView graph = findViewById(R.id.graphique);
//        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
//                new DataPoint(1, stats.get(0).getPoids()),
//                new DataPoint(2, stats.get(1).getPoids()),
//                new DataPoint(3, stats.get(2).getPoids()),
//                new DataPoint(4, stats.get(3).getPoids()),
//                new DataPoint(5, stats.get(4).getPoids())
//        });
//
//        sd.close();
//        graph.addSeries(series);
//
//        graph.getViewport().setMinX(0);
//        graph.getViewport().setMaxX(6);
//        graph.getViewport().setMinY(0);
//        graph.getViewport().setMaxY(110);
//
//        graph.getViewport().setYAxisBoundsManual(true);
//        graph.getViewport().setXAxisBoundsManual(true);
//
//        graph.getGridLabelRenderer().setHorizontalAxisTitle("Nombre de répétitions");
//        graph.getGridLabelRenderer().setVerticalAxisTitle("Poids soulevés");
//        graph.getGridLabelRenderer().setHorizontalLabelsVisible(true);
//        graph.getGridLabelRenderer().setVerticalLabelsVisible(true);
//
//        graph.setTitle("Poids soulevés en fonction de la série");
//        graph.setTitleTextSize(50);
//
//        series.setSpacing(50);
//        series.setValuesOnTopSize(50);
//        series.setDrawValuesOnTop(true);
//
//    }
}
