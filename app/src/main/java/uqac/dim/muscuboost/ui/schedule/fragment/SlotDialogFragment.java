package uqac.dim.muscuboost.ui.schedule.fragment;

import android.app.Dialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.R;
import uqac.dim.muscuboost.core.schedule.Day;
import uqac.dim.muscuboost.core.schedule.ISlottable;
import uqac.dim.muscuboost.core.training.Training;
import uqac.dim.muscuboost.db.TrainingDAO;

public class SlotDialogFragment extends BottomSheetDialogFragment {

    protected List<? extends ISlottable> slottables;
    protected OnSlotSubmit onSlotSubmit;

    protected View contentView;

    protected TextView title;
    protected Spinner daysSpinner;
    protected TimePicker timePicker;
    protected ListView slotsList;
    protected TextView emptyListText;
    protected Button addBtn;

    @Override
    public void setupDialog(Dialog dialog, int style) {
        contentView = View.inflate(getContext(),
                R.layout.schedule_add_fragment, null);

        dialog.setContentView(contentView);

        title = contentView.findViewById(R.id.title);
        daysSpinner = contentView.findViewById(R.id.days_spinner);
        timePicker = contentView.findViewById(R.id.time_picker);
        slotsList = contentView.findViewById(R.id.slots);
        emptyListText = contentView.findViewById(R.id.empty_list);
        addBtn = contentView.findViewById(R.id.add);
    }

    @Override
    public void onStart() {
        super.onStart();

        final int MAX_SLOTS_SHOWN = 5;

        daysSpinner.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, getWrappedWeek()));

        timePicker.setIs24HourView(DateFormat.is24HourFormat(getContext()));

        if(slottables != null && !slottables.isEmpty()) {
            slotsList.setAdapter(new ArrayAdapter<>(getContext(),
                    android.R.layout.simple_list_item_single_choice, getWrappedSlottables()));
            slotsList.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    Math.min(slottables.size(), MAX_SLOTS_SHOWN) * 100));
            slotsList.setVisibility(View.VISIBLE);
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

                if (onSlotSubmit != null)
                    onSlotSubmit.onSubmit(day, hour, minute, slottable);
                dismiss();
            }
        });

        contentView.findViewById(R.id.add_training)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editText = contentView.findViewById(R.id.new_name);
                        String trainingName = editText.getText().toString().trim();
                        editText.getText().clear();

                        if(!trainingName.isEmpty()) {
                            TrainingDAO trainingDao = new TrainingDAO(getContext());
                            trainingDao.open();

                            Training training = trainingDao.insert(trainingName);
                            ArrayAdapter<Wrapper<Training>> adapter =
                                    (ArrayAdapter<Wrapper<Training>>) slotsList.getAdapter();
                            adapter.add(new Wrapper<>(training, training.getName()));
                            adapter.notifyDataSetChanged();

                            trainingDao.close();
                        }
                    }
                });
    }

    public void setSlottables(List<? extends ISlottable> slottables) {
        this.slottables = slottables;
    }

    public void setOnSlotSubmit(OnSlotSubmit callback) {
        onSlotSubmit = callback;
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

    public interface OnSlotSubmit {
        void onSubmit(Day day, int hour, int minute, ISlottable item);
    }

}
