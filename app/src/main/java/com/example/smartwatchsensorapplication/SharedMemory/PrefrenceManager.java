package com.example.smartwatchsensorapplication.SharedMemory;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefrenceManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "socket_server";

    private static final String IS_AUTHENTICATED = "isAuthenticated";
    private static final String IP_ADDRESS = "isIPAddress";

    public PrefrenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setAutheticationLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_AUTHENTICATED, isFirstTime);
        editor.commit();
    }

    public void setIpAddress(String IPAddress) {
        editor.putString(IP_ADDRESS, IPAddress);
        editor.commit();
    }

    public boolean isAuthenticated() {
        return pref.getBoolean(IS_AUTHENTICATED, false);
    }

    public String getIpAddress() {
        return pref.getString(IP_ADDRESS, "");
    }


}
