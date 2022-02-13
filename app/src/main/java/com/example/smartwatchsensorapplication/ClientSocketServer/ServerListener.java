package com.example.smartwatchsensorapplication.ClientSocketServer;

public interface ServerListener {

    public void connectedToServer();
    public void disconnectedToServer();
    public void serverReadyForDataCollection();
    public void startForDataCollection();
    public void pauseForDataCollection();
    public void stopForDataCollection();

}
