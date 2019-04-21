package uqac.dim.muscuboostgraph.graph;

import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboostgraph.R;
import uqac.dim.muscuboostgraph.core.training.Statistics;
import uqac.dim.muscuboostgraph.db.ExerciseDAO;
import uqac.dim.muscuboostgraph.db.MuscleDAO;
import uqac.dim.muscuboostgraph.db.StatExerciseDAO;
import uqac.dim.muscuboostgraph.db.StatisticsDAO;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Graphique.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Graphique#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Graphique extends Fragment {

    public Graphique() {
        // Required empty public constructor
    }

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_graphique, container, false);

        int type = getArguments().getInt("type");
        String name = getArguments().getString("name");

        if(type == ListOptionGraph.TYPE_MUSCLE){
            StatisticsDAO sd = new StatisticsDAO(getActivity());
            ExerciseDAO ed = new ExerciseDAO(getActivity());
            MuscleDAO md = new MuscleDAO(getActivity());

            md.open();
            ed.open();
            sd.open();

            int id = md.selectIdOf(name);
            List<Integer> ids_exo = ed.selectAllIdWhereMuscle(id);
            DataPoint[] seriesPoint = sd.averageWeightForEachDateWhereExercice(ids_exo);

            sd.close();

            FrameLayout f = view.findViewById(R.id.graphique_container);
            f.addView(createGraphique(seriesPoint));
        }
        else{

        }
        return inflater.inflate(R.layout.fragment_graphique, container, false);
    }

    private View createGraphique(DataPoint[] seriesPoint){
        View v = new View(getActivity());
        if(seriesPoint.length == 0){
            ((TextView)v).setText("Aucune donnée");
        }
        else{
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(seriesPoint);

            v = new GraphView(getActivity());
            ((GraphView)v).addSeries(series);
        }

        return v;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
