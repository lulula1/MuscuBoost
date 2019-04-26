package uqac.dim.muscuboost;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import uqac.dim.muscuboost.core.person.Body;
import uqac.dim.muscuboost.core.person.EditBodyDetailsDialogFragment;
import uqac.dim.muscuboost.core.person.Person;
import uqac.dim.muscuboost.db.BodyDAO;
import uqac.dim.muscuboost.db.PersonDAO;

public class ProfilActivity extends AppCompatActivity {

    TextView nom, prenom, age, masse, taille, date_saisie, imc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        PersonDAO personDAO = new PersonDAO(getApplicationContext());
        personDAO.open();
        Person person = personDAO.select();
        personDAO.close();

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        age = findViewById(R.id.age);

        nom.setText(person.getName());
        prenom.setText(person.getSurname());
        age.setText(person.calculAge() + " ans");

        BodyDAO bodyDAO = new BodyDAO(getApplicationContext());
        bodyDAO.open();
        Body body = bodyDAO.selectLast();
        bodyDAO.close();

        masse = findViewById(R.id.masse);
        taille = findViewById(R.id.taille);
        date_saisie = findViewById(R.id.date_saisie);
        imc = findViewById(R.id.imc);

        masse.setText(body.getMasse() + " kg");
        taille.setText(body.getTaille() + " cm");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        date_saisie.setText("Dernière donnée saisie le " + df.format(body.getDate_enregistrement()));
        imc.setText(String.format("%.2f",body.calculIMC()));

        RelativeLayout relativeLayout = findViewById(R.id.graph_imc_container);
        bodyDAO = new BodyDAO(getApplicationContext());
        bodyDAO.open();
        if(bodyDAO.nbValeur() < 5){
            TextView tv = new TextView(getApplicationContext());
            tv.setText("Nombre de données insuffisants pour un graphique");
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            relativeLayout.addView(tv);
        }
        else{
            relativeLayout.addView(createGraphique(bodyDAO.getAll()));
            relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    400));
        }
        bodyDAO.close();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditBodyDetailsDialogFragment dialogFragment = new EditBodyDetailsDialogFragment();
                dialogFragment.setParent(ProfilActivity.this);
                if(!dialogFragment.isAdded())
                    dialogFragment.show(getSupportFragmentManager(), "editBodyDetails");

            }
        });
    }

    private GraphView createGraphique(DataPoint[] datas) {
        GraphView v;
        LineGraphSeries<DataPoint> seriesGraph = new LineGraphSeries<>(datas);

        seriesGraph.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                Toast toast = Toast.makeText(getApplicationContext(), "Date : "+ df.format(new Date((long)dataPoint.getX()))
                        + "\nPoids : " + dataPoint.getY() + " lbs", Toast.LENGTH_LONG);
                TextView tv = toast.getView().findViewById(android.R.id.message);
                if( tv != null) tv.setGravity(Gravity.CENTER);
                toast.show();
            }
        });

        seriesGraph.setDrawDataPoints(true);
        seriesGraph.setDataPointsRadius(12);

        v = new GraphView(getApplicationContext());
        v.addSeries(seriesGraph);

        v.getGridLabelRenderer().setLabelFormatter(
                new DateAsXAxisLabelFormatter(getApplicationContext(), new SimpleDateFormat("dd/MM/yyyy")));

        v.getViewport().setMaxX(seriesGraph.getHighestValueX());
        v.getViewport().setMinX(seriesGraph.getLowestValueX());
        v.getViewport().setMinY(0);
        v.getViewport().setMaxY(seriesGraph.getHighestValueY()*1.10);

        v.getViewport().setYAxisBoundsManual(true);
        v.getViewport().setXAxisBoundsManual(true);

        v.getGridLabelRenderer().setHumanRounding(false);
        v.getGridLabelRenderer().setNumHorizontalLabels(3);

        seriesGraph.setTitle("Masse/date");

        v.getLegendRenderer().setVisible(true);
        v.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);

        seriesGraph.setAnimated(true);

        v.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        return v;
    }

    public void update(){
        PersonDAO personDAO = new PersonDAO(getApplicationContext());
        personDAO.open();
        Person person = personDAO.select();
        personDAO.close();

        nom = findViewById(R.id.nom);
        prenom = findViewById(R.id.prenom);
        age = findViewById(R.id.age);

        nom.setText(person.getName());
        prenom.setText(person.getSurname());
        age.setText(person.calculAge() + " ans");

        BodyDAO bodyDAO = new BodyDAO(getApplicationContext());
        bodyDAO.open();
        Body body = bodyDAO.selectLast();
        bodyDAO.close();

        masse = findViewById(R.id.masse);
        taille = findViewById(R.id.taille);
        date_saisie = findViewById(R.id.date_saisie);
        imc = findViewById(R.id.imc);

        masse.setText(body.getMasse() + " kg");
        taille.setText(body.getTaille() + " cm");
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        date_saisie.setText("Dernière donnée saisie le " + df.format(body.getDate_enregistrement()));
        imc.setText(String.format("%.2f",body.calculIMC()));

        RelativeLayout relativeLayout = findViewById(R.id.graph_imc_container);
        bodyDAO = new BodyDAO(getApplicationContext());
        bodyDAO.open();
        if(bodyDAO.nbValeur() < 5){
            TextView tv = new TextView(getApplicationContext());
            tv.setText("Nombre de données insuffisants pour un graphique");
            tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            relativeLayout.addView(tv);
        }
        else{
            relativeLayout.addView(createGraphique(bodyDAO.getAll()));
            relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    400));
        }
    }

}
