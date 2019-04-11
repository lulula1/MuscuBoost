package uqac.dim.muscuboost;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.core.ongoingtraining.OngoingTraining;
import uqac.dim.muscuboost.core.training.Exercise;
import uqac.dim.muscuboost.core.training.Training;

public class TrainService extends Service {

    public static final String EXTRA_TRAINING = "training";

    private static final String NOTIF_ACTION = ".TrainService.NOTIF_ACTION";

    private static final String CHANNEL_ID = "training_channel";
    private static final int ID_NOTIFICATION = 12349;

    private TrainReceiver trainReceiver = new TrainReceiver(this);

    private OngoingTraining ongoingTraining;
    private NotificationManager notifManager;
    private PendingIntent contentPending;
    private PendingIntent nextSeriePending;
    private PendingIntent nextExercisePending;


    @Override
    public void onCreate() {
        super.onCreate();
        notifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        registerReceiver(trainReceiver, new IntentFilter(NOTIF_ACTION));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Training training = (Training) intent.getSerializableExtra(EXTRA_TRAINING);
        if(training == null)
            stopSelf();
        else {
            ongoingTraining = new OngoingTraining(training);

            setupNotification();
            sendNotification();
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(trainReceiver);
        cancelNotification();
    }

    public OngoingTraining getOngoingTraining() {
        return ongoingTraining;
    }

    public void update() {
        sendNotification();
    }

    private void setupNotification() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    getString(R.string.notif_training), NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(getString(R.string.notif_training_desc));
            notifManager.createNotificationChannel(channel);
        }

        contentPending = PendingIntent.getActivity(getBaseContext(), 0,
                new Intent(getBaseContext(), TrainActivity.class), 0);

        Intent nextSerieIntent = new Intent(NOTIF_ACTION);
        nextSerieIntent.putExtra(TrainReceiver.EXTRA_ACTION, TrainReceiver.ACTION_NEXT_SERIES);
        nextSeriePending = PendingIntent.getBroadcast(getBaseContext(), 1,
                nextSerieIntent, 0);

        Intent nextExerciseIntent = new Intent(NOTIF_ACTION);
        nextExerciseIntent.putExtra(TrainReceiver.EXTRA_ACTION, TrainReceiver.ACTION_NEXT_EXERCISE);
        nextExercisePending = PendingIntent.getBroadcast(getBaseContext(), 2,
                nextExerciseIntent, 0);
    }

    private void sendNotification() {
        Notification.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            builder = new Notification.Builder(getBaseContext(), CHANNEL_ID);
        else
            builder = new Notification.Builder(getBaseContext());

        builder.setSmallIcon(R.drawable.baseline_fitness_center_black_24)
                .setOngoing(true)
                .setContentTitle(getString(R.string.train_name) + " | "
                        + ongoingTraining.getTraining().getName())
                .setContentIntent(contentPending);

        if(!ongoingTraining.isTrainingOver())
            builder.setContentText(getString(R.string.series) + " #" + ongoingTraining.getSeries()
                    + " - " + ongoingTraining.getCurrentExercise().getName())
                    .addAction(new Notification.Action(R.drawable.baseline_exposure_plus_1_black_24,
                            getString(R.string.series).toLowerCase(), nextSeriePending))
                    .addAction(new Notification.Action(R.drawable.baseline_fast_forward_black_24,
                            getString(R.string.exercise).toLowerCase(), nextExercisePending));
        else
            builder.setContentText(getString(R.string.training_over));

        notifManager.notify(ID_NOTIFICATION, builder.build());
    }

    private void cancelNotification() {
        notifManager.cancel(ID_NOTIFICATION);
    }

}
