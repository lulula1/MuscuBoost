package uqac.dim.muscuboost;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private MonService monService;
    private ServiceConnection monServiceConnection;
    boolean mBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent bindIntent = new Intent(this, MonService.class);
        setServiceConnection();
        bindService(bindIntent, monServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void clickChrono(View v) {
        startActivity( new Intent(getBaseContext(), Chronometre.class));
    }

    public void clickEntrainement(View v) {
        startActivity(new Intent(getBaseContext(), ScheduleActivity.class));
    }

    public void clickExercice(View v) {
        startActivity(new Intent(getBaseContext(), ExerciceActivity.class));
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
}
