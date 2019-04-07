package uqac.dim.muscuboost;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import uqac.dim.muscuboost.core.training.Training;

public class TrainActivity extends AppCompatActivity {

    public static String EXTRA_TRAINING = "training";

    private final int ID_NOTIFICATION = 12349;
    private Training training;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.train_activity);

        training = (Training) getIntent().getSerializableExtra(EXTRA_TRAINING);
        if(training == null) {
            finish();
            return;
        }

        setupNotifications();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("training", training);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        training = (Training) savedInstanceState.getSerializable("training");
    }

    private void setupNotifications() {
        final String CHANNEL_ID = "training_channel";
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    getString(R.string.notif_training), NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(getString(R.string.notif_training_desc));
            nm.createNotificationChannel(channel);
            builder = new Notification.Builder(getBaseContext(), CHANNEL_ID);
        }else {
            builder = new Notification.Builder(getBaseContext());
        }

        PendingIntent contentPending = PendingIntent.getActivity(getBaseContext(), 0,
                new Intent(getBaseContext(), TrainActivity.class), 0);

        Intent nextSerieIntent = new Intent(getBaseContext(), TrainReceiver.class);
        nextSerieIntent.putExtra(TrainReceiver.EXTRA_ACTION, TrainReceiver.NEXT_SERIES);
        PendingIntent nextSeriePending = PendingIntent.getBroadcast(getBaseContext(), 1,
                nextSerieIntent, 0);

        Intent nextExerciseIntent = new Intent(getBaseContext(), TrainReceiver.class);
        nextExerciseIntent.putExtra(TrainReceiver.EXTRA_ACTION, TrainReceiver.NEXT_EXERCISE);
        PendingIntent nextExercisePending = PendingIntent.getBroadcast(getBaseContext(), 2,
                nextExerciseIntent, 0);

        builder.setSmallIcon(R.drawable.baseline_fitness_center_black_24)
                .setContentTitle(getString(R.string.train_name) + " | " + training.getName())
                // TODO - Set proper contentText
                .setContentText(getString(R.string.series) + " #" + 1 + " - " + "Développé-couché")
                .setContentIntent(contentPending)
                .addAction(new Notification.Action(R.drawable.baseline_exposure_plus_1_black_24,
                        getString(R.string.series).toLowerCase(), nextSeriePending))
                .addAction(new Notification.Action(R.drawable.baseline_fast_forward_black_24,
                        getString(R.string.exercise).toLowerCase(), nextExercisePending))
                .setOngoing(true);

        nm.notify(ID_NOTIFICATION, builder.build());
    }

    void cancelNotification() {
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.cancel(ID_NOTIFICATION);
    }

}
