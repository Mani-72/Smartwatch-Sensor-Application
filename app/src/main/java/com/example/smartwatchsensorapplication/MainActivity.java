package com.example.smartwatchsensorapplication;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.wearable.activity.WearableActivity;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.smartwatchsensorapplication.ClientSocketServer.Client;
import com.example.smartwatchsensorapplication.ClientSocketServer.ServerListener;
import com.example.smartwatchsensorapplication.OperationalClasses.AccelerationSensor;
import com.example.smartwatchsensorapplication.OperationalClasses.AccelerometerLinearSensor;
import com.example.smartwatchsensorapplication.OperationalClasses.GyroscopeSensor;
import com.example.smartwatchsensorapplication.OperationalClasses.HeartRateSensor;
import com.example.smartwatchsensorapplication.OperationalClasses.SensorDataListener;
import com.example.smartwatchsensorapplication.SharedMemory.PrefrenceManager;

public class MainActivity extends WearableActivity implements ServerListener, SensorDataListener {

    private TextView mStopWatch;
    AccelerationSensor accelerationSensor;
    AccelerometerLinearSensor accelerometerLinearSensor;
    GyroscopeSensor gyroscopeSensor;
    HeartRateSensor heartRateSensor;
    Client client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAmbientEnabled();
        //Enables Always-on
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setupViewsToCollectData();
    }


    private void setupViewsToCollectData() {
        mStopWatch = (TextView) findViewById(R.id.timer);

        connectToServer();
        //define instances
        defineInstances();

    }


    private void connectToServer() {
        PrefrenceManager prefManager;
        prefManager = new PrefrenceManager(this);
        String IP = prefManager.getIpAddress();
        client = new Client(this, this, IP);
        client.connectServer();

    }

    public void connectedToServer() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStopWatch.setText("Connected");
            }
        });

    }

    @Override
    public void disconnectedToServer() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStopWatch.setText("Disconnected");
            }
        });
        makeVibrations();
    }

    @Override
    public void serverReadyForDataCollection() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // TODO (Server Ready for data collection)
                mStopWatch.setText("Server Ready");
            }
        });
    }

    @Override
    public void startForDataCollection() {
        startDataCollection();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStopWatch.setText("Start");
            }
        });
    }

    @Override
    public void pauseForDataCollection() {
        pauseDataCollection();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStopWatch.setText("Pause");
            }
        });
    }

    @Override
    public void stopForDataCollection() {
        stopDataCollection();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mStopWatch.setText("Stop");
            }
        });
    }

    @Override
    public void accelerometerData(String data) {
        client.sendDate(data);
    }

    @Override
    public void accelerometerDataLinear(String data) {
        client.sendDate(data);
    }

    @Override
    public void gyroscopeData(String data) {
        client.sendDate(data);
    }

    private void defineInstances() {
        // For Sensor Manager
        accelerationSensor = new AccelerationSensor(this, this);
        gyroscopeSensor = new GyroscopeSensor(this, this);
        accelerometerLinearSensor = new AccelerometerLinearSensor(this, this);
        heartRateSensor = new HeartRateSensor(this, this);
    }


    private void startDataCollection() {
        // TODO (Start Collecting Data)
        accelerationSensor.startDataCollection();
        heartRateSensor.startDataCollection();
        gyroscopeSensor.startDataCollection();
        accelerometerLinearSensor.startDataCollection();
    }

    private void pauseDataCollection() {
        // TODO (Pause Collecting Data)
        accelerationSensor.pauseDataCollection();
        heartRateSensor.pauseDataCollection();
        gyroscopeSensor.pauseDataCollection();
        accelerometerLinearSensor.pauseDataCollection();
    }

    private void stopDataCollection() {
        // TODO (Stop Collecting Data)
        resetData(); // Unregister the sensors listeners
    }


    private void makeVibrations() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        long[] vibrationPattern = {0, 500, 50, 300};
        //-1 - don't repeat
        final int indexInPatternToRepeat = -1;
        vibrator.vibrate(vibrationPattern, indexInPatternToRepeat);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.onDestroy();
        resetData();
    }

    private void resetData() {
        accelerationSensor.stopDataCollection();
        heartRateSensor.stopDataCollection();
        gyroscopeSensor.stopDataCollection();
        accelerometerLinearSensor.stopDataCollection();

    }


}