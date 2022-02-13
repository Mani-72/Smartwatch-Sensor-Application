package com.example.smartwatchsensorapplication.OperationalClasses;

import android.os.SystemClock;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeStamp {

    public static String getTimestamp() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss:SSS");
        return sdf.format(new Date());
    }

    public static String getTimeinMiliSeconds(Long timestamp){
        Long timeInMills = System.currentTimeMillis() + (timestamp - SystemClock.elapsedRealtimeNanos()) / 1000000;
         return String.valueOf(timeInMills);
    }

}
