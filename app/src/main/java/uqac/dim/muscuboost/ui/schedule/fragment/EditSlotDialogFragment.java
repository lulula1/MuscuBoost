package uqac.dim.muscuboost.ui.schedule.fragment;

import android.app.Dialog;
import android.os.Build;
import android.util.Log;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.schedule.ScheduleSlot;

public class EditSlotDialogFragment extends AddSlotDialogFragment {

    private ScheduleSlot editedSlot;

    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        if(getArguments() != null) {
            Object obj = getArguments().getSerializable("slot");
            if(obj instanceof ScheduleSlot)
                editedSlot = (ScheduleSlot) obj;
        }

        title.setText(R.string.edit);
    }

    @Override
    public void onStart() {
        super.onStart();

        if(editedSlot == null) {
            Log.e("DIM", "editedSlot not found");
            dismiss();
            return;
        }

        daysSpinner.setSelection((int) editedSlot.getDay().getId());

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            timePicker.setHour(editedSlot.getHour() != null ? editedSlot.getHour() : 0);
            timePicker.setMinute(editedSlot.getMinute() != null ? editedSlot.getMinute() : 0);
        }

        for(int i = 0; i < slottables.size(); i++) {
            if(slottables.get(i).equals(editedSlot.getItem())) {
                slotsList.setItemChecked(i, true);
                addBtn.setEnabled(true);
                break;
            }
        }


    }

}
