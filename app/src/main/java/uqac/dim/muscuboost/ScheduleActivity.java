package uqac.dim.muscuboost;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import uqac.dim.muscuboost.core.schedule.Day;
import uqac.dim.muscuboost.core.schedule.Schedule;
import uqac.dim.muscuboost.core.schedule.ScheduleSlot;
import uqac.dim.muscuboost.core.training.Training;

public class ScheduleActivity extends AppCompatActivity {

    private Schedule<Training> schedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO - Handle fab properly
                Toast.makeText(getBaseContext(),
                        "Soit patient, cette fonctionnalité n'est pas encore implémentée...",
                        Toast.LENGTH_SHORT).show();
                schedule.addSlot(new ScheduleSlot<>(Day.WEDNESDAY, 12, 34,
                        new Training(0, "TrainingX")));
                drawSchedule(schedule);
            }
        });

        // TODO - Remove test values
        schedule = new Schedule<>();
        schedule.addSlot(new ScheduleSlot<>(Day.MONDAY,
                new Training(0, "Training1")));
        schedule.addSlot(new ScheduleSlot<>(Day.MONDAY, 9, 0,
                new Training(1, "Training2")));
        schedule.addSlot(new ScheduleSlot<>(Day.SUNDAY, 19, null,
                new Training(2, "Training3")));
    }

    @Override
    protected void onStart() {
        super.onStart();
        drawSchedule(schedule);
    }

    private String getStringResource(String resName) {
        return getResources().getString(
                getResources().getIdentifier(resName, "string", getPackageName()));
    }

    private void drawSchedule(Schedule<?> schedule) {
        TableLayout scheduleView = findViewById(R.id.schedule);
        scheduleView.removeAllViewsInLayout();

        for(Day day : Day.getWeek()) {
            // Create day header template
            TableRow headerTemplate = (TableRow) getLayoutInflater()
                    .inflate(R.layout.schedule_header_template, scheduleView, false);
            ((TextView) headerTemplate.findViewById(R.id.day))
                    .setText(getStringResource(day.toString()));
            scheduleView.addView(headerTemplate);

            if(schedule.getSlotsByDay(day).size() > 0) {
                for(ScheduleSlot<?> slot : schedule.getSlotsByDay(day)) {
                    // Create schedule slot row template
                    TableRow rowTemplate = (TableRow) getLayoutInflater()
                            .inflate(R.layout.schedule_row_template, scheduleView, false);

                    ((TextView) rowTemplate.findViewById(R.id.time))
                            .setText(slot.getFormattedTime());
                    ((TextView) rowTemplate.findViewById(R.id.label))
                            .setText(slot.getLabel());
                    rowTemplate.findViewById(R.id.menu_btn)
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    // TODO - Implement onClick
                                }
                            });

                    scheduleView.addView(rowTemplate);
                }
            }else {
                // Create empty row template
                getLayoutInflater().inflate(R.layout.schedule_empty_template, scheduleView);
            }
        }
    }

}
