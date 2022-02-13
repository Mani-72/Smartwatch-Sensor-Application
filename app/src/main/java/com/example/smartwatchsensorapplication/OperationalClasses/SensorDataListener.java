package com.example.smartwatchsensorapplication.OperationalClasses;

public interface SensorDataListener {
    public void accelerometerData(String data);
    public void accelerometerDataLinear(String data);
    public void gyroscopeData(String data);
}
