package com.example.smartwatchsensorapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smartwatchsensorapplication.SharedMemory.PrefrenceManager;

public class IPActivity extends WearableActivity {

    EditText edtIPAddress;
    Button btnNext;
    String IPAddress = "";
    private PrefrenceManager prefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking for first time launch - before calling setContentView()
        prefManager = new PrefrenceManager(this);
        if (prefManager.isAuthenticated()) {
            moveToNextActivity();
            finish();
        }

        setContentView(R.layout.activity_i_p);

        setAmbientEnabled();
        //Enables Always-on
        setupViews();
    }

    private void moveToNextActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }


    private void setupViews() {
        edtIPAddress = (EditText) findViewById(R.id.edt_ip);
        btnNext = (Button) findViewById(R.id.btn_next);

        setupOnClickListener();
    }

    private void setupOnClickListener() {

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                peoceedToSaveData();
            }
        });

    }

    private void peoceedToSaveData() {
        IPAddress = edtIPAddress.getText().toString();
        if (IPAddress.isEmpty()) {
            Toast toast = Toast.makeText(this, "Enter Server IP Address to Proceed", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            prefManager.setAutheticationLaunch(true);
            prefManager.setIpAddress(IPAddress);
            moveToNextActivity();
        }
    }
}