package com.example.smartwatchsensorapplication.OperationalClasses;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.smartwatchsensorapplication.ObjectClasses.SensorAxisData;

public class AccelerationSensor implements SensorEventListener {

    int count = 0;
    Context context;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    SensorDataListener sensorDataListener;


    public AccelerationSensor(Context context, SensorDataListener sensorDataListener) {
        this.context = context;
        this.sensorDataListener =sensorDataListener;
        // Initialize Accelerometer Sensor
        defineInstances();
    }

    private void defineInstances() {
        //define instances
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }


    public void startDataCollection() {
        sensorManager.registerListener(this, accelerometer, sensorManager.SENSOR_DELAY_FASTEST);
    }

    public void pauseDataCollection() {
        sensorManager.unregisterListener(this);
    }

    public void stopDataCollection() {
        if (sensorManager != null)
            sensorManager.unregisterListener(this);
        resetData();
    }

    private void resetData() {
        //TODO: Reset Data
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //get the current values of the accelerometer for each axis
        float current_xValue = sensorEvent.values[0];
        float current_yValue = sensorEvent.values[1];
        float current_zValue = sensorEvent.values[2];
        long timeStamp = sensorEvent.timestamp;

         /* Log.i("Test", "X: "+current_xValue);
          Log.i("Test", "Y: "+current_yValue);
          Log.i("Test", "Z: "+current_zValue);
          count++;
          Log.i("TestCount", TimerForDataCollection.stopWatch + "-"+count);*/

        loadLiveDataonSocketServer(new SensorAxisData(TimeStamp.getTimeinMiliSeconds(timeStamp), current_xValue, current_yValue, current_zValue));

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //accelerometer does not report accuracy changes
    }

    private void loadLiveDataonSocketServer(SensorAxisData sensorAxisData) {
      sensorDataListener.accelerometerData("ACC/"+sensorAxisData.getA_timer()+"/"+sensorAxisData.getB_xValue()+"/"+sensorAxisData.getC_yValue()+"/"+sensorAxisData.getD_zValue());
    }




}
