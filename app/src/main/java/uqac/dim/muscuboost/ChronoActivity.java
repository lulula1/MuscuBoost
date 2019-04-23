package uqac.dim.muscuboost;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ChronoActivity extends AppCompatActivity implements ServiceConnection {

    private ChronoService chronoService;
    private boolean serviceBound = false;

    private TextView txtTimer;
    private Button btnStart;
    private LinearLayout lapContainer, playingSection, pausedSection;
    private Handler customHandler = new Handler();

    private Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            txtTimer.setText(getTxtTimer());
            customHandler.postDelayed(this, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chronometer_activity);

        Intent bindIntent = new Intent(this, ChronoService.class);
        bindService(bindIntent, this, Context.BIND_AUTO_CREATE);

        txtTimer = findViewById(R.id.timer_value);
        lapContainer = findViewById(R.id.lap_container);
        btnStart = findViewById(R.id.btn_start);
        playingSection = findViewById(R.id.playing_section);
        pausedSection = findViewById(R.id.paused_section);

        txtTimer.setText("00:00:00");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(serviceBound)
            loadServiceDetails();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (serviceBound)
            unbindService(this);
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        chronoService = ((ChronoService.ChronoBinder) service).getService();
        customHandler.postDelayed(updateTimerThread, 0);
        serviceBound = true;

        loadServiceDetails();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        chronoService = null;
        customHandler.removeCallbacks(updateTimerThread);
        serviceBound = false;
    }

    public void startService() {
        startService(new Intent(getBaseContext(), ChronoService.class));
    }

    public void stopService() {
        stopService(new Intent(getBaseContext(), ChronoService.class));
    }

    public void startChrono(View view) {
        startService();
        chronoService.startChrono();
        setVisibleSection(playingSection);
    }

    public void pauseChrono(View view) {
        chronoService.pauseChrono();
        setVisibleSection(pausedSection);
    }

    public void lapChrono(View view) {
        addLapView(chronoService.lapChrono());
    }

    public void resumeChrono(View view) {
        chronoService.resumeChrono();
        setVisibleSection(playingSection);
    }

    public void resetChrono(View view) {
        stopService();
        chronoService.resetChrono();

        lapContainer.removeAllViews();

        setVisibleSection(btnStart);
    }

    public String getTxtTimer() {
        return chronoService.getTxtTimer();
    }

    private void setVisibleSection(View view) {
        btnStart.setVisibility(View.GONE);
        playingSection.setVisibility(View.GONE);
        pausedSection.setVisibility(View.GONE);
        view.setVisibility(View.VISIBLE);
    }

    private void addLapView(ChronoService.Lap lap) {
        View addView = getLayoutInflater()
                .inflate(R.layout.lap_row, lapContainer, false);
        ((TextView) addView.findViewById(R.id.id))
                .setText("#" + lap.getLapId());
        ((TextView) addView.findViewById(R.id.timer))
                .setText(lap.getLapTxtTimer());
        lapContainer.addView(addView);
    }

    private void loadServiceDetails() {
        // Load time
        txtTimer.setText(chronoService.getTxtTimer());

        // Load laps
        for (ChronoService.Lap lap : chronoService.getLaps())
            addLapView(lap);

        // Load appropriate buttons
        switch (chronoService.getStatus()) {
            case STOPPED:
                setVisibleSection(btnStart);
                break;
            case RUNNING:
                setVisibleSection(playingSection);
                break;
            case PAUSED:
                setVisibleSection(pausedSection);
                break;
        }
    }

    // TODO - Remove unused code
/*
    protected void notifie(){

        final int ID_NOTIFICATION = 1234;
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        if (Build.VERSION.SDK_INT > 25) {
            String channelId = "MyChannelId";
            CharSequence channelName = "MyChannelName";
            int importance = NotificationManager.IMPORTANCE_LOW;
            NotificationChannel notificationChannel = new NotificationChannel(channelId,channelName,importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            nm.createNotificationChannel(notificationChannel);


            Notification n = new Notification.Builder(getApplicationContext(),channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Chronomètre")
                    .setContentText(getTxtTimer())
                    .setContentIntent(pendingIntent)
                    .build();

            nm.notify(ID_NOTIFICATION, n);
        }else{
            Notification n = new Notification.Builder(getApplicationContext())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Chronomètre")
                    .setContentText(getTxtTimer())
                    .setContentIntent(pendingIntent)
                    .build();

            nm.notify(ID_NOTIFICATION, n);
        }


    }*/

}
