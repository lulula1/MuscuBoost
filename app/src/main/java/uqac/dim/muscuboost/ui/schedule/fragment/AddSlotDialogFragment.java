package uqac.dim.muscuboost.ui.schedule.fragment;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.schedule.Day;
import uqac.dim.muscuboost.core.schedule.ISlottable;

public class AddSlotDialogFragment extends BottomSheetDialogFragment {

    private OnSlotAdded onSlotAdded;

    private Spinner daysSpinner;
    private TimePicker timePicker;
    private Button addBtn;

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(),
                R.layout.schedule_add_fragment, null);

        daysSpinner = contentView.findViewById(R.id.days_spinner);
        timePicker = contentView.findViewById(R.id.time_picker);
        addBtn = contentView.findViewById(R.id.add);

        dialog.setContentView(contentView);
    }

    @Override
    public void onStart() {
        super.onStart();
        daysSpinner.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, getWrappedWeek()));
        timePicker.setIs24HourView(DateFormat.is24HourFormat(getContext()));
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Day day = ((Wrapper<Day>) daysSpinner.getSelectedItem()).getItem();
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                // TODO - Handle item select
                ISlottable item = new ISlottable() {
                    @Override
                    public String getSlotLabel() {
                        return "Placeholder";
                    }
                };

                if(onSlotAdded != null)
                    onSlotAdded.onAdd(day, hour, minute, item);
                dismiss();
            }
        });
    }

    private List<Wrapper<Day>> getWrappedWeek() {
        List<Wrapper<Day>> week = new ArrayList<>();
        for(Day day : Day.getWeek()) {
            int nameResId = getResources()
                    .getIdentifier(day.toString(), "string", getContext().getPackageName());
            week.add(new Wrapper<>(day, getResources().getString(nameResId)));
        }
        return week;
    }

    public void setOnSlotAdded(OnSlotAdded callback) {
        onSlotAdded = callback;
    }

    public interface OnSlotAdded {
        void onAdd(Day day, int hour, int minute, ISlottable item);
    }

}
