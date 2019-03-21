package uqac.dim.muscuboost.ui.schedule.fragment;

import java.util.Calendar;

public class AddSlotDialogFragment extends SlotDialogFragment {

    @Override
    public void onStart() {
        super.onStart();

        daysSpinner.setSelection((Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 5) % 7);
    }

}
