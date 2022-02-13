package com.example.smartwatchsensorapplication.ClientSocketServer;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Client {
    Context context;
    public static final int SERVER_PORT = 5050;
    public static String SERVER_IP = "192.168.178.30";
    private ClientThread clientThread;
    private Thread thread;
    private Handler handler;
    private ServerListener serverListener;


    public Client(Context context, ServerListener serverListener, String IP) {
        this.context = context;
        this.serverListener = serverListener;
        SERVER_IP = IP;
    }

    public void showMessage(final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    public void connectServer() {
        handler = new Handler();
        clientThread = new ClientThread(this);
        thread = new Thread(clientThread);
        thread.start();
    }

    /*Call back methods for Server Control*/

    public void connectedToServer() {
        serverListener.connectedToServer();
    }

    public void disconnectedToServer() {
        serverListener.disconnectedToServer();
    }

    public void readyForDataCollection() {
        serverListener.serverReadyForDataCollection();
    }

    public void startForDataCollection() {
        serverListener.startForDataCollection();
    }

    public void pauseForDataCollection() {
        serverListener.pauseForDataCollection();
    }

    public void stopForDataCollection() {
        serverListener.stopForDataCollection();
    }

    public void sendDate(String clientMessage) {
        if (null != clientThread) {
            if (clientMessage.length() > 0) {
                clientThread.sendMessage(clientMessage);
            }
        }
    }

    public void onDestroy() {
        if (null != clientThread) {
            clientThread.sendMessage("Disconnect");
            clientThread = null;
        }
    }

}



