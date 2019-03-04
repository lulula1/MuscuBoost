package uqac.dim.muscuboost.ui.schedule;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.schedule.ScheduleSlot;
import uqac.dim.muscuboost.core.training.Training;

public class TrainingSlotAction implements IScheduleSlotAction {

    @Override
    public int getMenuResId() {
        return R.menu.schedule_data_menu;
    }

    @Override
    public void onLabelClick(Context context, View view, ScheduleSlot<?> slot) {
        // TODO - Implement the training display action
        Training training = (Training) slot.getItem();
        Toast.makeText(context, "Afficher l'entrainement #" + training.getId(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onMenuItemClick(Context context, MenuItem item, ScheduleSlot<?> slot) {
        Training training = (Training) slot.getItem();
        switch(item.getItemId()) {
            case R.id.edit:
                // TODO - Implement the training edit action
                Toast.makeText(context, "Modifier l'entrainement #" + training.getId(),
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                // TODO - Implement the training delete action
                Toast.makeText(context, "Supprimer l'entrainement #" + training.getId(),
                        Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

}
