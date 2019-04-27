package uqac.dim.muscuboost.graph;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.Identifiable;
import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.core.training.Muscle;
import uqac.dim.muscuboost.db.DAOSingleKey;
import uqac.dim.muscuboost.db.ExerciseDAO;
import uqac.dim.muscuboost.db.MuscleDAO;
import uqac.dim.muscuboost.ui.Wrapper;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ListOptionGraph.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ListOptionGraph#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListOptionGraph extends ListFragment {

    public static final int TYPE_MUSCLE = 0;
    public static final int TYPE_EXERCICE = 1;

    public ListOptionGraph() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ListOptionGraph.
     */
    // TODO: Rename and change types and number of parameters
    public static ListOptionGraph newInstance(int type) {
        ListOptionGraph fragment = new ListOptionGraph();
        Bundle args = new Bundle();
        args.putInt("type", type);
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

        if(getArguments().getInt("type") == TYPE_EXERCICE){
            ExerciseDAO ed = new ExerciseDAO(getActivity());
            ed.open();
            ArrayAdapter<Wrapper<Exercise>> adapter = new ArrayAdapter<>
                    (getActivity(), android.R.layout.simple_list_item_1, Wrapper.wrap(ed.getAll(),
                            new Wrapper.GetItemNameListener<Exercise>() {
                                @Override
                                public String getItemName(Exercise item) {
                                    return item.getName();
                                }
                            }));
            setListAdapter(adapter);
            ed.close();
        }
        else{
            MuscleDAO md = new MuscleDAO(getActivity());
            md.open();
            ArrayAdapter<Wrapper<Muscle>> adapter = new ArrayAdapter<>
                    (getActivity(), android.R.layout.simple_list_item_1, Wrapper.wrap(md.getAll(),
                            new Wrapper.GetItemNameListener<Muscle>() {
                                @Override
                                public String getItemName(Muscle item) {
                                    return item.getName();
                                }
                            }));
            setListAdapter(adapter);
            md.close();
        }
        return inflater.inflate(R.layout.list_option_graph_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        Log.i("DIM", "Click sur Item - position : " + position);
        getActivity().getFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_graph_container,
                        Graphique.newInstance(getArguments().getInt("type"),
                                ((Wrapper<Identifiable>) listView.getAdapter().getItem(position))
                                        .getItem().getId()))
                .addToBackStack(null)
                .commit();

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
