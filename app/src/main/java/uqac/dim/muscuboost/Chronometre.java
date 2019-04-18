package uqac.dim.muscuboost;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Chronometre extends AppCompatActivity {

    public static final String VisBtnStart = "BTN_START" ;
    public static final String VisBtnPause = "BTN_PAUSE";
    public static final String VisBtnLap = "BTN_LAP";
    public static final String VisBtnResume = "BTN_RESUME";
    public static final String VisBtnRestart = "BTN_RESTART";
    public static final String Run = "RUNNING";

    private MonService monService;
    private ServiceConnection monServiceConnection;
    boolean mBound = false;

    Button btnStart, btnPause, btnLap, btnReset, btnResume;
    TextView txtTimer ;
    LinearLayout container;
    Handler customHandler = new Handler();

    SharedPreferences sharedPreferences ;
    boolean running;
    int lap;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            txtTimer.setText(monService.getTxtTimer());
            customHandler.postDelayed(this,0);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chronometer);

        Intent bindIntent = new Intent(this, MonService.class);
        setServiceConnection();
        bindService(bindIntent, monServiceConnection, Context.BIND_AUTO_CREATE);


        btnStart = (Button) findViewById(R.id.btnStart);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnLap = (Button) findViewById(R.id.btnLap);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnResume = (Button) findViewById(R.id.btnResume);
        txtTimer = (TextView) findViewById(R.id.timerValue);
        container = (LinearLayout) findViewById(R.id.container);

        sharedPreferences = this.getSharedPreferences("LOAD_PREF",MODE_PRIVATE);



        if(sharedPreferences.contains(Run))
             LoadPreferences();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!running){
                    demarrerService();
                    startChrono();
                    customHandler.postDelayed(updateTimerThread,0);
                    btnStart.setVisibility(View.GONE);
                    btnPause.setVisibility(View.VISIBLE);
                    btnLap.setVisibility(View.VISIBLE);
                    running = true;
                }
            }

        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(running){
                    PauseChrono();
                    btnPause.setVisibility(View.GONE);
                    btnLap.setVisibility(View.GONE);
                    btnResume.setVisibility(View.VISIBLE);
                    btnReset.setVisibility(View.VISIBLE);
                    running = false;
                }
            }
        });

        btnLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View addView = inflater.inflate(R.layout.row,null);
                TextView txtTour = (TextView) addView.findViewById(R.id.txtTour);
                TextView txtTemps = (TextView) addView.findViewById(R.id.txtTemps);
                lap = LapChrono();
                txtTour.setText("Tour :  "+String.format("%02d",lap));
                CharSequence time = getTxtTimer();
                txtTemps.setText("Temps :  "+time);
                container.addView(addView);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetChrono();
                arreterService();

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();

                container.removeAllViews();

                btnResume.setVisibility(View.GONE);
                btnReset.setVisibility(View.GONE);
                btnStart.setVisibility(View.VISIBLE);
            }
        });

        btnResume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!running){
                    ResumeChrono();
                    btnResume.setVisibility(View.GONE);
                    btnReset.setVisibility(View.GONE);
                    btnPause.setVisibility(View.VISIBLE);
                    btnLap.setVisibility(View.VISIBLE);
                    running = true;
                }
            }
        });
    }

    private void setServiceConnection() {
        monServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceDisconnected(ComponentName name) {
                monService = null;
            }

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                monService = ((MonService.MonBinderDActivite) service).getMonService();
                mBound = true;
            }
        };
    }

    public void demarrerService() {
        Intent serviceIntent = new Intent(this, MonService.class);
        startService(serviceIntent);
    }

    public void arreterService() {
        Intent serviceIntent = new Intent(this, MonService.class);
        stopService(serviceIntent);

    }

    public void startChrono() {
       monService.startChrono();
    }

    public void PauseChrono() {
        monService.PauseChrono();
    }

    public int LapChrono() {
        int lap = monService.LapChrono();
        return lap;
    }

    public void ResetChrono() {
        monService.ResetChrono();
    }

    public void ResumeChrono() {
        monService.ResumeChrono();
    }

    public String getTxtTimer(){
        String txtTimer = monService.getTxtTimer();
        return txtTimer;
    }

/*
    @Override
    protected void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        Log.i("DIM","SAVE");
        outState.putInt("SaveBtnStart",btnStart.getVisibility());
        outState.putInt("SaveBtnPause",btnPause.getVisibility());
        outState.putInt("SaveBtnLap",btnLap.getVisibility());
        outState.putInt("SaveBtnReset",btnReset.getVisibility());
        outState.putInt("SaveBtnResume",btnResume.getVisibility());
        outState.putBoolean("MyBoolean", running);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        Log.i("DIM","RESTOR");
        if(savedInstanceState != null){
            btnStart.setVisibility(savedInstanceState.getInt("SaveBtnStart"));
            btnPause.setVisibility(savedInstanceState.getInt("SaveBtnPause"));
            btnLap.setVisibility(savedInstanceState.getInt("SaveBtnLap"));
            btnReset.setVisibility(savedInstanceState.getInt("SaveBtnReset"));
            btnResume.setVisibility(savedInstanceState.getInt("SaveBtnResume"));
            running = savedInstanceState.getBoolean("MyBoolean");

        }
    }
*/

    private void SavePreferences(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(VisBtnStart,btnStart.getVisibility());
        editor.putInt(VisBtnPause,btnPause.getVisibility());
        editor.putInt(VisBtnLap,btnLap.getVisibility());
        editor.putInt(VisBtnRestart,btnReset.getVisibility());
        editor.putInt(VisBtnResume,btnResume.getVisibility());
        editor.putBoolean(Run, running);
        editor.commit();
        Toast.makeText(getApplicationContext(),"SAVE PREFERENCE",Toast.LENGTH_SHORT).show();
    }

    private void LoadPreferences(){
        customHandler.postDelayed(updateTimerThread,0);
        btnStart.setVisibility(sharedPreferences.getInt(VisBtnStart,btnStart.getVisibility()));
        btnPause.setVisibility(sharedPreferences.getInt(VisBtnPause,btnPause.getVisibility()));
        btnLap.setVisibility(sharedPreferences.getInt(VisBtnLap,btnLap.getVisibility()));
        btnReset.setVisibility(sharedPreferences.getInt(VisBtnRestart,btnReset.getVisibility()));
        btnResume.setVisibility(sharedPreferences.getInt(VisBtnResume,btnResume.getVisibility()));
        running = sharedPreferences.getBoolean(Run, running);
        Toast.makeText(getApplicationContext(),"LOAD PREFERENCE",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SavePreferences();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        arreterService();
        if (mBound) {
            unbindService(monServiceConnection);
            mBound = false;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    @Override
    public void onPause(){
        super.onPause();
        SavePreferences();
        //notifie();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(sharedPreferences.contains(Run))
            LoadPreferences();

    }

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
