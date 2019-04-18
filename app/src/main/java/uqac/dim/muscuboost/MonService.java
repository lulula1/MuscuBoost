package uqac.dim.muscuboost;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class MonService extends Service {

    String txtTimer;
    Handler customHandler = new Handler();


    boolean running = false;
    int lap=0;

    long startTime=0L, timeInMilliseconds=0L, timeSwapBuff=0L, updateTime=0L;

    private IBinder monBinder;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis()-startTime;
            updateTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) updateTime/1000;
            int mins =  secs/60;
            secs%=60;
            int milliseconds=(int) (updateTime%1000);
            txtTimer = (""+String.format("%02d",mins)+":"+String.format("%02d",secs)+":"+String.format("%02d",milliseconds).substring(0,2));
            customHandler.postDelayed(this,0);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        monBinder = new MonBinderDActivite();
        Log.i("DIM","SERVICE.ONCREATE");
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.i("DIM","SERVICE.ONBIND");
        return monBinder;
    }

    @Override
    public boolean onUnbind(Intent intent)
    {
        Log.i("DIM","SERVICE.ONUNBIND");
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("DIM","SERVICE.ONDESTROY");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public class MonBinderDActivite extends Binder {
        MonService getMonService() {
            Log.i("DIM","SERVICE.MONBINDERDACTIVITE.getMonService");
            return MonService.this;
        }
    }

    public void startChrono(){
        Log.i("DIM","SERVICE.START");
        if(!running){
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread,0);
            running = true;
        }
    }

    public void PauseChrono() {
        Log.i("DIM","SERVICE.PAUSE");
        if(running){
            timeSwapBuff+=timeInMilliseconds;
            customHandler.removeCallbacks(updateTimerThread);
            running = false;
        }
    }

    public int LapChrono() {
        Log.i("DIM","SERVICE.LAP");
        lap++;
        return lap;
    }

    public void ResetChrono() {
        Log.i("DIM","SERVICE.RESET");
        startTime=0L;
        timeInMilliseconds=0L;
        timeSwapBuff=0L;
        updateTime=0L;
        lap=0;
        txtTimer="00:00:00";
    }

    public void ResumeChrono() {
        Log.i("DIM","SERVICE.RESUME");
        if(!running){
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread,0);
            running = true;
        }
    }

    public String getTxtTimer(){
        Log.i("DIM","SERVICE.GETTEXTTIMER");
        return txtTimer;
    }

}

