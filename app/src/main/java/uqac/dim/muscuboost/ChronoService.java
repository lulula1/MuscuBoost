package uqac.dim.muscuboost;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ChronoService extends Service {

    private Handler customHandler = new Handler();

    private List<Lap> laps = new ArrayList<>();

    private long startTime = 0L, timeInMilliseconds = 0L, timeSwapBuff = 0L, updateTime = 0L;

    private ChronoStatus status = ChronoStatus.STOPPED;
    private IBinder binder;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMilliseconds;
            customHandler.postDelayed(this, 0);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new ChronoBinder();
        Log.i("DIM", "CHRONO_SERVICE.ONCREATE");
    }


    @Override
    public IBinder onBind(Intent intent) {
        Log.i("DIM", "CHRONO_SERVICE.ONBIND");
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("DIM", "CHRONO_SERVICE.ONUNBIND");
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("DIM", "CHRONO_SERVICE.ONDESTROY");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    public List<Lap> getLaps() {
        return laps;
    }

    public ChronoStatus getStatus() {
        return status;
    }

    public void startChrono() {
        if (status != ChronoStatus.RUNNING) {
            Log.i("DIM", "CHRONO_SERVICE.START");
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread, 0);
            status = ChronoStatus.RUNNING;
        }
    }

    public void pauseChrono() {
        if (status == ChronoStatus.RUNNING) {
            Log.i("DIM", "CHRONO_SERVICE.PAUSE");
            timeSwapBuff += timeInMilliseconds;
            customHandler.removeCallbacks(updateTimerThread);
            status = ChronoStatus.PAUSED;
        }
    }

    public Lap lapChrono() {
        Log.i("DIM", "CHRONO_SERVICE.LAP");
        Lap lap = new Lap(laps.size() + 1, getTxtTimer());
        laps.add(lap);
        return lap;
    }

    public void resetChrono() {
        Log.i("DIM", "CHRONO_SERVICE.RESET");
        startTime = 0L;
        timeInMilliseconds = 0L;
        timeSwapBuff = 0L;
        updateTime = 0L;
        laps.clear();
        status = ChronoStatus.STOPPED;
    }

    public void resumeChrono() {
        if (status != ChronoStatus.RUNNING) {
            Log.i("DIM", "CHRONO_SERVICE.RESUME");
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread, 0);
            status = ChronoStatus.RUNNING;
        }
    }

    public String getTxtTimer() {
        int secs = (int) updateTime / 1000;
        int mins = secs / 60;
        secs %= 60;
        int milliseconds = (int) updateTime % 1000;
        return String.format("%02d", mins) + ":" + String.format("%02d", secs) + ":"
                + String.format("%02d", milliseconds).substring(0, 2);
    }

    class ChronoBinder extends Binder {
        ChronoService getService() {
            Log.i("DIM", "SERVICE.CHRONOSERVICE.GETSERVICE");
            return ChronoService.this;
        }
    }

    static class Lap {

        private int lapId;
        private String lapTxtTimer;

        public Lap(int lapId, String lapTxtTimer) {
            this.lapId = lapId;
            this.lapTxtTimer = lapTxtTimer;
        }

        public int getLapId() {
            return lapId;
        }

        public String getLapTxtTimer() {
            return lapTxtTimer;
        }

    }

    enum ChronoStatus {
        STOPPED, RUNNING, PAUSED
    }

}

