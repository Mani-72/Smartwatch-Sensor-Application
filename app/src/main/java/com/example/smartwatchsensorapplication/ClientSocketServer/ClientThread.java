package com.example.smartwatchsensorapplication.ClientSocketServer;

import android.graphics.Color;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.smartwatchsensorapplication.ClientSocketServer.Client.SERVER_IP;
import static com.example.smartwatchsensorapplication.ClientSocketServer.Client.SERVER_PORT;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class ClientThread implements Runnable {

    // client socket
    private Socket socket;
    private BufferedReader input;
    Client client;

    public ClientThread(Client client) {
        this.client = client;
    }

    @Override
    public void run() {

        try {

            InetAddress serverAddr = InetAddress.getByName(SERVER_IP);
            client.showMessage("Connecting to Phone...");

            socket = new Socket(serverAddr, SERVER_PORT);

            if (socket.isBound()) {

                client.showMessage("Connected to Phone...");
                client.connectedToServer();
                // processMessageForDataCollection("Server Ready"); // Test Case

            }


            while (!Thread.currentThread().isInterrupted()) {

                Log.i("Test", "run: Server ****...");
                this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message = input.readLine();
                Log.i("Test", "run: Phone Disconnected..." + message);
                if (null == message || "Disconnect".contentEquals(message)) {
                    Thread.interrupted();
                    message = "Phone Disconnected...";
                    client.showMessage(message);
                    client.disconnectedToServer();
                    break;
                }
                client.showMessage("Server: " + message);
                processMessageForDataCollection(message);
            }

        } catch (UnknownHostException e1) {
            e1.printStackTrace();
            client.showMessage(e1.getMessage());
        } catch (IOException e1) {
            client.showMessage("Problem Connecting to server...");
            Thread.interrupted();
            e1.printStackTrace();
        } catch (NullPointerException e3) {
            client.showMessage("error returned");
        }

    }

    private void processMessageForDataCollection(String message) {
        if (message.equals("Server Ready")) {
            client.readyForDataCollection();
        } else if (message.equals("Phone Server Start")) {
            client.startForDataCollection();
        } else if (message.equals("Phone Server Pause")) {
            client.pauseForDataCollection();
        } else if (message.equals("Phone Server Stop")) {
            client.stopForDataCollection();
        }

    }


    public void sendMessage(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (null != socket) {
                        PrintWriter out = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(socket.getOutputStream())),
                                true);
                        out.println(message);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(new Date());
    }


    public String doEncryption(String data) {
        Log.i("Original Data Result: ", data);
        String encryption = "";
        try {
            String key = "Bar12345Bar12345"; // 128 bit key
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            encryption = encrypted.toString();

            Log.i("Data Encryption Result: ", encryption);

        } catch (Exception e) {
            Log.e("EncryptionError", "doEncryption: " + e.getMessage());
        }
        return encryption;

    }

}
