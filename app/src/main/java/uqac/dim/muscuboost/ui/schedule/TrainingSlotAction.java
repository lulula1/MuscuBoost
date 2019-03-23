package uqac.dim.muscuboost.ui.schedule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.ScheduleActivity;
import uqac.dim.muscuboost.TrainingActivity;
import uqac.dim.muscuboost.core.schedule.Day;
import uqac.dim.muscuboost.core.schedule.ISlottable;
import uqac.dim.muscuboost.core.schedule.ScheduleSlot;
import uqac.dim.muscuboost.core.training.Training;
import uqac.dim.muscuboost.db.SlotDAO;
import uqac.dim.muscuboost.db.TrainingDAO;
import uqac.dim.muscuboost.ui.schedule.fragment.AddSlotDialogFragment;
import uqac.dim.muscuboost.ui.schedule.fragment.EditSlotDialogFragment;

public class TrainingSlotAction implements IScheduleSlotAction {

    private Training getTraining(ScheduleSlot slot) {
        return slot.getItem() instanceof Training
                ? (Training) slot.getItem()
                : null;
    }

    private ScheduleActivity getActivity(Context context) {
        return context instanceof ScheduleActivity
                ? (ScheduleActivity) context
                : null;
    }

    @Override
    public int getMenuResId() {
        return R.menu.schedule_data_menu;
    }

    @Override
    public void onLabelClick(Context context, View view, ScheduleSlot slot) {
        Training training = getTraining(slot);
        if(training == null) return;

        Intent intent = new Intent(context, TrainingActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(TrainingActivity.EXTRA_TRAINING, training);
        context.startActivity(intent);
    }

    @Override
    public boolean onMenuItemClick(final Context context, MenuItem item, final ScheduleSlot slot) {
        final ScheduleActivity activity = getActivity(context);
        if(activity == null) return false;

        Training training = getTraining(slot);
        if(training == null) return false;

        final SlotDAO slotDao = new SlotDAO(context);
        slotDao.open();

        TrainingDAO trainingDao = new TrainingDAO(context);
        trainingDao.open();

        switch(item.getItemId()) {
            case R.id.edit:
                Bundle editSlotArguments = new Bundle();
                editSlotArguments.putSerializable("slot", slot);

                final EditSlotDialogFragment editSlotFragment = new EditSlotDialogFragment();
                editSlotFragment.setArguments(editSlotArguments);

                editSlotFragment.setOnSlotSubmit(new AddSlotDialogFragment.OnSlotSubmit() {
                    @Override
                    public void onSubmit(Day day, int hour, int minute, ISlottable item) {
                        slot.setDay(day);
                        slot.setHour(hour);
                        slot.setMinute(minute);
                        slot.setItem(item);

                        slotDao.update(slot);
                        slotDao.close();

                        activity.drawSchedule();
                    }
                });

                editSlotFragment.setSlottables(trainingDao.getAll());
                if(!editSlotFragment.isAdded())
                    editSlotFragment.show(activity.getSupportFragmentManager(), "editSlot");
                break;
            case R.id.delete:
                // TODO - Ask for confirmation

                slotDao.delete(slot.getId());
                slotDao.close();

                activity.getSchedule().removeSlot(slot);
                activity.drawSchedule();
                break;
        }
        trainingDao.close();
        return true;
    }

}
