package uqac.dim.muscuboost;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TrainReceiver extends BroadcastReceiver {

    public static final String EXTRA_ACTION = "action";

    public static final int NEXT_SERIES = 1;
    public static final int NEXT_EXERCISE = 2;

    @Override
    public void onReceive(Context context, Intent intent) {
        int action = intent.getIntExtra(EXTRA_ACTION, -1);
        switch(action) {
            case NEXT_SERIES:
                // TODO - Handle next serie
                System.out.println("next serie");
                break;
            case NEXT_EXERCISE:
                // TODO - Handle next exercise
                System.out.println("next exercice");
                break;
        }
    }

}
