package tools;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import base.BaseActivity;
import base.BaseApplication;

public class PrefManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public PrefManager() {
        pref = BaseApplication.getContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    // shared pref mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private String PREF_NAME = "androidhive-settings";
    private String fingerprintEnabled = "FingerprintEnabled";
    private String userName = "UserName";

    public boolean getFingerprintEnabled() {
        return pref.getBoolean(this.fingerprintEnabled, false);
    }

    public void setFingerprintEnabled(boolean fingerprintEnabled) {
        editor.putBoolean(this.fingerprintEnabled, fingerprintEnabled);
        editor.commit();
    }

    public String getUserName() {
        return pref.getString(this.userName , "");
    }

    public void setUserName(String userName) {
        editor.putString(this.userName , userName);
        editor.commit();
    }






}