package uqac.dim.muscuboost;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TrainReceiver extends BroadcastReceiver {

    public static final String EXTRA_ACTION = "action";

    public static final int ACTION_NEXT_SERIES = 1;
    public static final int ACTION_NEXT_EXERCISE = 2;

    private TrainService trainService;

    public TrainReceiver(TrainService trainService) {
        this.trainService = trainService;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int action = intent.getIntExtra(EXTRA_ACTION, -1);
        switch(action) {
            case ACTION_NEXT_SERIES:
                trainService.nextSeries();
                break;
            case ACTION_NEXT_EXERCISE:
                trainService.nextExercise();
                break;
            default:
                return;
        }
        trainService.update();
    }

}
