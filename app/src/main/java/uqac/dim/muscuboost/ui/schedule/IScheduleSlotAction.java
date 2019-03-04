package uqac.dim.muscuboost.ui.schedule;

import android.content.Context;
import android.view.MenuItem;
import android.view.View;

import uqac.dim.muscuboost.core.schedule.ScheduleSlot;

public interface IScheduleSlotAction {

    int getMenuResId();
    void onLabelClick(Context context, View view, ScheduleSlot slot);
    boolean onMenuItemClick(Context context, MenuItem item, ScheduleSlot slot);

}
