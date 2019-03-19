package uqac.dim.muscuboost.ui.schedule.fragment;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.schedule.Day;
import uqac.dim.muscuboost.core.schedule.ISlottable;

public class AddSlotDialogFragment extends BottomSheetDialogFragment {

    private List<ISlottable> slottables;
    private OnSlotAdded onSlotAdded;

    private Spinner daysSpinner;
    private TimePicker timePicker;
    private ListView slotsList;
    private TextView emptyListText;
    private Button addBtn;

    @Override
    public void setupDialog(Dialog dialog, int style) {
        View contentView = View.inflate(getContext(),
                R.layout.schedule_add_fragment, null);

        daysSpinner = contentView.findViewById(R.id.days_spinner);
        timePicker = contentView.findViewById(R.id.time_picker);
        slotsList = contentView.findViewById(R.id.slots);
        emptyListText = contentView.findViewById(R.id.empty_list);
        addBtn = contentView.findViewById(R.id.add);

        dialog.setContentView(contentView);
    }

    @Override
    public void onStart() {
        super.onStart();

        final int MAX_SLOTS_SHOWN = 5;

        daysSpinner.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, getWrappedWeek()));
        daysSpinner.setSelection((Calendar.getInstance().get(Calendar.DAY_OF_WEEK) + 5) % 7);

        timePicker.setIs24HourView(DateFormat.is24HourFormat(getContext()));

        if(slottables != null && !slottables.isEmpty()) {
            slotsList.setAdapter(new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_list_item_single_choice, getWrappedSlottables()));
            slotsList.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    Math.min(slottables.size(), MAX_SLOTS_SHOWN) * 100));
            emptyListText.setVisibility(View.GONE);
        }

        slotsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                addBtn.setEnabled(true);
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Day day = ((Wrapper<Day>) daysSpinner.getSelectedItem()).getItem();
                int hour = timePicker.getCurrentHour();
                int minute = timePicker.getCurrentMinute();
                ISlottable slottable = ((Wrapper<ISlottable>) slotsList.getItemAtPosition(
                        slotsList.getCheckedItemPosition())).getItem();

                if (onSlotAdded != null)
                    onSlotAdded.onAdd(day, hour, minute, slottable);
                dismiss();
            }
        });
    }

    private List<Wrapper<Day>> getWrappedWeek() {
        List<Wrapper<Day>> weekWrappers = new ArrayList<>();
        for(Day day : Day.getWeek()) {
            int nameResId = getResources()
                    .getIdentifier(day.toString(), "string", getContext().getPackageName());
            weekWrappers.add(new Wrapper<>(day, getResources().getString(nameResId)));
        }
        return weekWrappers;
    }

    private List<Wrapper<ISlottable>> getWrappedSlottables() {
        List<Wrapper<ISlottable>> slottableWrappers = new ArrayList<>();
        if(slottables != null)
            for(ISlottable slottable : slottables)
                slottableWrappers.add(new Wrapper<>(slottable, slottable.getSlotLabel()));
        return slottableWrappers;
    }

    public void setSlottables(List<ISlottable> slottables) {
        this.slottables = slottables;
    }

    public void setOnSlotAdded(OnSlotAdded callback) {
        onSlotAdded = callback;
    }

    public interface OnSlotAdded {
        void onAdd(Day day, int hour, int minute, ISlottable item);
    }

}
