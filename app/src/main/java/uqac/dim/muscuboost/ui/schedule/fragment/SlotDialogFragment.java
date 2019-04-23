package uqac.dim.muscuboost.ui.schedule.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.design.widget.BottomSheetDialogFragment;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
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
import uqac.dim.muscuboost.db.SlotDAO;
import uqac.dim.muscuboost.db.TrainingDAO;
import uqac.dim.muscuboost.ui.dialog.ConfirmDialog;
import uqac.dim.muscuboost.ui.dialog.EditTextDialog;

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

        daysSpinner.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_dropdown_item, getWrappedWeek()));

        timePicker.setIs24HourView(DateFormat.is24HourFormat(getContext()));

        slotsList.setAdapter(new ArrayAdapter<>(getContext(),
                android.R.layout.simple_list_item_single_choice, getWrappedSlottables()));
        updateSlotsList();

        slotsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long arg3) {
                addBtn.setEnabled(true);
            }
        });
        registerForContextMenu(slotsList);

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

                        if (!trainingName.isEmpty()) {
                            TrainingDAO trainingDao = new TrainingDAO(getContext());
                            trainingDao.open();

                            Training training = trainingDao.insert(trainingName);
                            ArrayAdapter<Wrapper<Training>> adapter =
                                    (ArrayAdapter<Wrapper<Training>>) slotsList.getAdapter();
                            adapter.add(new Wrapper<>(training, training.getName()));
                            adapter.notifyDataSetChanged();
                            updateSlotsList();

                            trainingDao.close();
                        }
                    }
                });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        final int CONTEXT_MENU_RENAME = 0;
        final int CONTEXT_MENU_DELETE = 1;

        menu.add(Menu.NONE, CONTEXT_MENU_RENAME, Menu.NONE, "Rename");
        menu.add(Menu.NONE, CONTEXT_MENU_DELETE, Menu.NONE, "Delete");

        MenuItem.OnMenuItemClickListener onMenuItemClickListener =
           new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                final ArrayAdapter adapter = (ArrayAdapter<Wrapper<?>>) slotsList.getAdapter();
                int itemPosition = ((AdapterView.AdapterContextMenuInfo) item.getMenuInfo())
                        .position;
                final Wrapper<?> wrapper = (Wrapper<?>) adapter.getItem(itemPosition);
                if(wrapper == null) return false;
                final Training training = (Training) wrapper.getItem();

                if (training == null)
                    return false;

                final SlotDAO slotDao = new SlotDAO(getContext());
                final TrainingDAO trainingDao = new TrainingDAO(getContext());
                slotDao.open();
                trainingDao.open();
                switch (item.getItemId()) {
                    case CONTEXT_MENU_RENAME:
                        new EditTextDialog(getContext())
                                .setTitle(R.string.rename_training)
                                .setText(training.getName())
                                .setOnSubmitListener(new EditTextDialog.OnSubmitListener() {
                                    @Override
                                    public void onSubmit(String value) {
                                        value = value.trim();
                                        if(!value.isEmpty()) {
                                            training.setName(value);
                                            trainingDao.update(training);
                                            trainingDao.close();
                                            adapter.clear();
                                            adapter.addAll(getWrappedSlottables());
                                            adapter.notifyDataSetChanged();
                                        }
                                    }
                                })
                                .show();
                        break;
                    case CONTEXT_MENU_DELETE:
                        DialogInterface.OnClickListener onClickListener =
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        trainingDao.delete(training.getId());
                                        adapter.remove(wrapper);
                                        trainingDao.close();
                                        adapter.notifyDataSetChanged();
                                    }
                                };
                        if(slotDao.isTrainingInUse(training.getId()))
                            new ConfirmDialog(getContext())
                                    .setTitle(R.string.confirm_delete_title)
                                    .setMessage(R.string.training_in_use)
                                    .setPositiveListener(onClickListener)
                                    .show();
                        else
                            onClickListener.onClick(null, 0);
                        break;
                }
                slotDao.close();
                return true;
            }
        };

        for (int i = 0; i < menu.size(); i++)
            menu.getItem(i).setOnMenuItemClickListener(onMenuItemClickListener);
    }

    public void setSlottables(List<? extends ISlottable> slottables) {
        this.slottables = slottables;
    }

    public void setOnSlotSubmit(OnSlotSubmit callback) {
        onSlotSubmit = callback;
    }

    private void updateSlotsList() {
        final int MAX_SLOTS_SHOWN = 5;
        boolean showList = !slotsList.getAdapter().isEmpty();

        if (showList)
            slotsList.setLayoutParams(new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    Math.min(slotsList.getAdapter().getCount(), MAX_SLOTS_SHOWN) * 100));
        slotsList.setVisibility(showList ? View.VISIBLE : View.GONE);
        emptyListText.setVisibility(showList ? View.GONE : View.VISIBLE);
    }

    private List<Wrapper<Day>> getWrappedWeek() {
        List<Wrapper<Day>> weekWrappers = new ArrayList<>();
        for (Day day : Day.getWeek()) {
            int nameResId = getResources()
                    .getIdentifier(day.toString(), "string", getContext().getPackageName());
            weekWrappers.add(new Wrapper<>(day, getResources().getString(nameResId)));
        }
        return weekWrappers;
    }

    private List<Wrapper<ISlottable>> getWrappedSlottables() {
        List<Wrapper<ISlottable>> slottableWrappers = new ArrayList<>();
        if (slottables != null)
            for (ISlottable slottable : slottables)
                slottableWrappers.add(new Wrapper<>(slottable, slottable.getSlotLabel()));
        return slottableWrappers;
    }

    public interface OnSlotSubmit {
        void onSubmit(Day day, int hour, int minute, ISlottable item);
    }

}
