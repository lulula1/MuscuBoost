package uqac.dim.muscuboost.graph;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.db.ExerciseDAO;
import uqac.dim.muscuboost.db.MuscleDAO;
import uqac.dim.muscuboost.db.StatisticsDAO;


public class Graphique extends Fragment {

    private Toast toast;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Graphique.
     */
    // TODO: Rename and change types and number of parameters
    public static Graphique newInstance(int type, String name) {
        Graphique fragment = new Graphique();
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("name", name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toast = Toast.makeText(getActivity(), "", Toast.LENGTH_LONG);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.graphique_fragment, container, false);

        int type = getArguments().getInt("type");
        String name = getArguments().getString("name");

        DataPoint[] seriesPoint;

        StatisticsDAO sd = new StatisticsDAO(getActivity());
        ExerciseDAO ed = new ExerciseDAO(getActivity());

        if(type == ListOptionGraph.TYPE_MUSCLE){
            MuscleDAO md = new MuscleDAO(getActivity());

            md.open();
            ed.open();
            sd.open();

            seriesPoint = sd.getAvgWeightForEachDate(
                    ed.getAllIdFromMuscleId(md.getId(name)));

            sd.close();

        }
        else{
           ed.open();
           sd.open();

           seriesPoint = sd.getAvgRepCountForEachDate(
                   (int) ed.getIdFromName(name));

           sd.close();

        }

        RelativeLayout relativeLayout = view.findViewById(R.id.graphique_container);

        if(seriesPoint.length <= 5){
            TextView tv = new TextView(getActivity());
            tv.setText(R.string.insufficient_data);
            tv.setTextSize(20);
            relativeLayout.addView(tv);
        }
        else{

            if(type == ListOptionGraph.TYPE_MUSCLE){
                relativeLayout.addView(createGraphiqueMuscle(seriesPoint));
            }
            else if (type == ListOptionGraph.TYPE_EXERCICE){
                relativeLayout.addView(createGraphiqueExercice(seriesPoint));
            }
        }

        return view;
    }

    private GraphView createGraphique(DataPoint[] seriesPoint,
                                      final String toastText, String seriesTitle){
        GraphView v;
        LineGraphSeries<DataPoint> seriesGraph = new LineGraphSeries<>(seriesPoint);

        double moyenne = 0;
        for (DataPoint aSeriesPoint : seriesPoint) {
            moyenne += aSeriesPoint.getY();
        }
        moyenne /= seriesPoint.length;

        LineGraphSeries<DataPoint> serieMoyenne = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(seriesGraph.getLowestValueX(), moyenne),
                new DataPoint(seriesGraph.getHighestValueX(), moyenne)
        });

        seriesGraph.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                String toastTextValue = String.format(toastText,
                df.format(new Date((long)dataPoint.getX())), dataPoint.getY());
                toast.setText(toastTextValue);
                TextView tv = toast.getView().findViewById(android.R.id.message);
                if(tv != null) tv.setGravity(Gravity.CENTER);
                toast.show();
            }
        });

        seriesGraph.setDrawDataPoints(true);
        seriesGraph.setDataPointsRadius(12);

        serieMoyenne.setColor(Color.RED);

        v = new GraphView(getActivity());
        v.addSeries(seriesGraph);
        v.addSeries(serieMoyenne);

        v.getGridLabelRenderer().setLabelFormatter(
                new DateAsXAxisLabelFormatter(getActivity(),
                        new SimpleDateFormat("dd/MM/yyyy")));

        v.getViewport().setMaxX(seriesGraph.getHighestValueX());
        v.getViewport().setMinX(seriesGraph.getLowestValueX());
        v.getViewport().setMinY(0);
        v.getViewport().setMaxY(seriesGraph.getHighestValueY()*1.10);

        v.getViewport().setYAxisBoundsManual(true);
        v.getViewport().setXAxisBoundsManual(true);

        v.getViewport().setScalable(true);
        v.getViewport().setScalableY(true);
        v.getGridLabelRenderer().setHumanRounding(false);
        v.getGridLabelRenderer().setNumHorizontalLabels(3);

        seriesGraph.setTitle(seriesTitle);
        serieMoyenne.setTitle(getString(R.string.global_average));
        v.getLegendRenderer().setVisible(true);
        v.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);

        seriesGraph.setAnimated(true);
        serieMoyenne.setAnimated(true);

        v.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        return v;
    }

    private GraphView createGraphiqueMuscle(DataPoint[] seriesPoint){
        return createGraphique(seriesPoint,
                getString(R.string.toast_by_muscle), getString(R.string.series_title_by_muscle));
    }

    private GraphView createGraphiqueExercice(DataPoint[] seriesPoint){
        return createGraphique(seriesPoint,
                getString(R.string.toast_by_exercise), getString(R.string.series_title_by_exercise));
    }

}
