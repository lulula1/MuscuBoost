package uqac.dim.muscuboost.ui.trainingshowcase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.training.Exercise;

public class ExerciseAdapter extends ArrayAdapter<Exercise> {

    private int resource;
    private boolean editing = false;

    public ExerciseAdapter(Context context, int resource, List<Exercise> exercise) {
        super(context, resource, exercise);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        Exercise exercise = getItem(position);

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(resource, null);
            viewHolder = new ViewHolder();
            viewHolder.name = convertView.findViewById(R.id.name);
            viewHolder.muscle = convertView.findViewById(R.id.muscle);
            viewHolder.remove = convertView.findViewById(R.id.remove);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name.setText(exercise.getName());
        viewHolder.muscle.setText(exercise.getMuscle().getName());

        if(viewHolder.remove != null)
            viewHolder.remove.setVisibility(editing ? View.VISIBLE : View.GONE);

        return convertView;
    }

    public void setEditing(boolean editing) {
        this.editing = editing;
    }

    public boolean isEditing() {
        return editing;
    }

    public void toggleEditing() {
        editing = !editing;
    }

    private class ViewHolder {
        TextView name;
        TextView muscle;
        ImageButton remove;
    }

}
