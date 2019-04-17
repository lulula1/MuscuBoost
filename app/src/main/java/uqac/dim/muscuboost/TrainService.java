package uqac.dim.muscuboost;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import java.util.ArrayList;
import java.util.List;

import uqac.dim.muscuboost.core.ongoingtraining.Observer;
import uqac.dim.muscuboost.core.ongoingtraining.OngoingTraining;
import uqac.dim.muscuboost.core.ongoingtraining.Subject;
import uqac.dim.muscuboost.core.training.Training;

public class TrainService extends Service implements Subject {

    public static final String EXTRA_TRAINING = "uqac.dim.muscuboost.TrainService.EXTRA_TRAINING";

    private static final String NOTIF_ACTION = "uqac.dim.muscuboost.TrainService.NOTIF_ACTION";

    private static final String CHANNEL_ID = "uqac.dim.muscuboost.TRAINING";
    private static final int NOTIFICATION_ID = 12349;

    private TrainReceiver trainReceiver = new TrainReceiver(this);
    private List<Observer> observers = new ArrayList<>();

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
    public int onStartCommand(Intent intent, int flags, int startId) {
        Training training = (Training) intent.getSerializableExtra(EXTRA_TRAINING);
        if(isDifferentTraining(training)) {
            ongoingTraining = new OngoingTraining(training);
            setupNotification();
            startForeground(NOTIFICATION_ID, sendNotification());
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new TrainBinder();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(trainReceiver);
        stopForeground(true);
    }

    @Override
    public void notifyUpdate() {
        for(Observer observer : observers)
            observer.onUpdate();
    }

    @Override
    public void register(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void unregister(Observer observer) {
        observers.remove(observer);
    }

    public OngoingTraining getOngoingTraining() {
        return ongoingTraining;
    }

    public void update() {
        sendNotification();
        notifyUpdate();
    }

    private boolean isDifferentTraining(Training training) {
        return training != null && (ongoingTraining == null
                || ongoingTraining.getTraining().getId() != training.getId());
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

    private Notification sendNotification() {
        if(notifManager == null || contentPending == null
            || nextSeriePending == null || nextExercisePending == null)
            throw new RuntimeException("You must first call setupNotification() " +
                    "before calling sendNotification()");

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
                    .addAction(new Notification.Action(R.drawable.baseline_exposure_plus_1_white_24,
                            getString(R.string.series).toLowerCase(), nextSeriePending))
                    .addAction(new Notification.Action(R.drawable.baseline_fast_forward_white_24,
                            getString(R.string.exercise).toLowerCase(), nextExercisePending));
        else
            builder.setContentText(getString(R.string.training_over));

        Notification notification = builder.build();
        notifManager.notify(NOTIFICATION_ID, notification);
        return notification;
    }

    void nextSeries() {
        ongoingTraining.nextSeries();
        update();
    }

    void nextExercise() {
        ongoingTraining.nextExercise();
        update();
    }

    class TrainBinder extends Binder {
        TrainService getService() {
            return TrainService.this;
        }
    }
}
