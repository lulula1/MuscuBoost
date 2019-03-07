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

public class AddSlotDialogFragment extends BottomSheetDialogFragment {

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
                Day selectedDay = ((Wrapper<Day>) daysSpinner.getSelectedItem()).getItem();
                int selectedHour = timePicker.getCurrentHour();
                int selectedMinutes = timePicker.getCurrentMinute();
                // Training selectedTraining = ...
                // TODO - Handle selected values

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

}
