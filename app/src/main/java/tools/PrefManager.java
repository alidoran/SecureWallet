package tools;

import android.content.SharedPreferences;

import com.google.gson.Gson;

import base.BaseApplication;
import models.SignUpModel;

public class PrefManager {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private static PrefManager prefManager;

    public static PrefManager getInstance() {
        if (prefManager == null)
            prefManager = new PrefManager();
        return prefManager;
    }


    public PrefManager() {
        pref = BaseApplication.getContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    // shared pref mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private String PREF_NAME = "Androidhive-settings";
    private String googleId = "googleId";
    private String persianLanguage = "PersianLanguage";
    private String firstBoot = "FirstBoot";
    private String signDetails = "signDetails";

    public boolean isPersianLanguage() {
        return pref.getBoolean(this.persianLanguage, false);
    }

    public void setPersianLanguage(boolean persianLanguage) {
        editor.putBoolean(this.persianLanguage, persianLanguage);
        editor.commit();
    }

    public boolean isFirstBoot() {
        return pref.getBoolean(this.firstBoot, true);
    }

    public void setFirstBoot(boolean firstBoot) {
        editor.putBoolean(this.firstBoot, firstBoot);
        editor.commit();
    }

    public String getGoogleId() {
        return pref.getString(this.googleId, "");
    }

    public void setGoogleId(String googleId) {
        editor.putString(this.googleId, "");
        editor.commit();
    }

    public SignUpModel getSignDetails() {
        String signUpDetailString = pref.getString(this.signDetails, "");
        if (!signUpDetailString.isEmpty())
            return new Gson().fromJson(signUpDetailString, SignUpModel.class);
        else
            return null;
    }

    public void setSignDetails(String signDetails) {
        editor.putString(this.signDetails, signDetails);
        editor.commit();
    }


}