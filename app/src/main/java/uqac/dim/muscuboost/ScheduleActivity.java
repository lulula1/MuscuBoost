package uqac.dim.muscuboost;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.core.schedule.Day;
import uqac.dim.muscuboost.core.schedule.ISlottable;
import uqac.dim.muscuboost.core.schedule.Schedule;
import uqac.dim.muscuboost.core.schedule.ScheduleSlot;
import uqac.dim.muscuboost.core.training.Training;
import uqac.dim.muscuboost.ui.schedule.IScheduleSlotAction;
import uqac.dim.muscuboost.ui.schedule.TrainingSlotAction;
import uqac.dim.muscuboost.ui.schedule.fragment.AddSlotDialogFragment;

public class ScheduleActivity extends AppCompatActivity {

    private Schedule schedule;
    private IScheduleSlotAction slotAction = new TrainingSlotAction();

    private AddSlotDialogFragment addSlotFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        addSlotFragment = new AddSlotDialogFragment();
        addSlotFragment.setOnSlotSubmit(new AddSlotDialogFragment.OnSlotSubmit() {
            @Override
            public void onSubmit(Day day, int hour, int minute, ISlottable item) {
                schedule.addSlot(new ScheduleSlot(0, day, hour, minute, item));
                drawSchedule();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!addSlotFragment.isAdded())
                    addSlotFragment.show(getSupportFragmentManager(), "addSlot");
            }
        });

        schedule = new Schedule();

        // TODO - Remove test values
        schedule.addSlot(new ScheduleSlot(0, Day.MONDAY, 19, 0,
                new Training(0, "Jambes")));
        schedule.addSlot(new ScheduleSlot(1, Day.TUESDAY, 19, 0,
                new Training(1, "Pectoraux")));
        schedule.addSlot(new ScheduleSlot(2, Day.THURSDAY, 19, 0,
                new Training(2, "Bras")));
        schedule.addSlot(new ScheduleSlot(3, Day.FRIDAY, 19, 0,
                new Training(3, "Dos")));
        schedule.addSlot(new ScheduleSlot(4, Day.SUNDAY, 17, 0,
                new Training(4, "Epaules")));
    }

    @Override
    protected void onStart() {
        super.onStart();
        drawSchedule();

        // TODO - Retrieve slots from the database
        List<ISlottable> slottables = new ArrayList<>();
        for(ScheduleSlot slot : schedule.getSlots())
            slottables.add(slot.getItem());
        //////////////////////////////////////////
        addSlotFragment.setSlottables(slottables);
    }

    public Schedule getSchedule() {
        return schedule;
    }

    private String getStringResource(String resName) {
        return getResources().getString(
                getResources().getIdentifier(resName, "string", getPackageName()));
    }

    public void drawSchedule() {
        TableLayout scheduleView = findViewById(R.id.schedule);
        scheduleView.removeAllViewsInLayout();

        for(Day day : Day.getWeek()) {
            createHeader(scheduleView, day);
            if(schedule.getSlotsByDay(day).size() > 0)
                for(ScheduleSlot slot : schedule.getSlotsByDay(day))
                    createRow(scheduleView, slot);
            else
                createEmptyRow(scheduleView);
        }
    }

    // Create day header
    private void createHeader(ViewGroup root, Day day) {
        TableRow header = (TableRow) getLayoutInflater()
                .inflate(R.layout.schedule_header_template, root, false);

        TextView dayLabel = header.findViewById(R.id.day);
        dayLabel.setText(getStringResource(day.toString()));

        root.addView(header);
    }

    // Create schedule slot row
    private void createRow(ViewGroup root, final ScheduleSlot slot) {
        TableRow row = (TableRow) getLayoutInflater()
                .inflate(R.layout.schedule_row_template, root, false);

        TextView timeLabel = row.findViewById(R.id.time);
        Button labelButton = row.findViewById(R.id.label);
        Button menuButton = row.findViewById(R.id.menu);

        timeLabel.setText(slot.getFormattedTime());
        labelButton.setText(slot.getLabel());

        labelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        slotAction.onLabelClick(ScheduleActivity.this, view, slot);
                    }
                });

        menuButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            PopupMenu popup = new PopupMenu(getBaseContext(), view);
                            popup.getMenuInflater().inflate(slotAction.getMenuResId(), popup.getMenu());
                            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                @Override
                                public boolean onMenuItemClick(MenuItem item) {
                                    return slotAction.onMenuItemClick(ScheduleActivity.this, item, slot);
                                }
                            });
                            popup.show();
                        }catch(Resources.NotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });

        root.addView(row);
    }

    // Create empty row
    private void createEmptyRow(ViewGroup root) {
        getLayoutInflater().inflate(R.layout.schedule_empty_template, root);
    }

}
