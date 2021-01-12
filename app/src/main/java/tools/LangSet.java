package tools;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.DisplayMetrics;

import java.util.Locale;

import base.BaseApplication;


public class LangSet {
    Context context;

    public LangSet(Context context){
        this.context=context;
        changeLangRecognize();
    }

    private void changeLangRecognize() {
        String lang=PrefManager.getInstance().isPersianLanguage() ? "fa" : "en" ;
        Locale locale;
        if (lang.equals("fa"))
            ChangeLocate(lang);
        else
            ChangeLocate(lang);
    }

    private void ChangeLocate(String localLoad){
        Locale locale=new Locale(localLoad);
        Locale.setDefault(locale);
        Configuration config= new Configuration();
        config.locale=locale;
        context.getResources().updateConfiguration(config,context.getResources().getDisplayMetrics());
    }


}
