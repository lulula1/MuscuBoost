package uqac.dim.muscuboost.ui.schedule.fragment;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;

import uqac.dim.muscuboost.R;

public class AddSlotDialogFragment extends BottomSheetDialogFragment {

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(),
                R.layout.schedule_add_fragment, null);
        dialog.setContentView(contentView);
    }

}
