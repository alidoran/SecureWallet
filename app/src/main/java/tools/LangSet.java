package tools;

import android.content.Context;
import android.content.res.Configuration;

import java.util.Locale;

public class LangSet {
    Context context;

    public LangSet(Context context) {
        this.context = context ;
        changeLangRecognize();
    }

    public void changeLangRecognize() {
        String lang = MySettings.isRtl ? "fa" : "en";

        if (lang.equals("fa")) {
            changeLocate(lang);
            MySettings.fontFamily = MySettings.persianFont;

        } else if (lang.equals("en")) {
            changeLocate(lang);
            MySettings.fontFamily = MySettings.englishFont;
        }
    }

    private void changeLocate(String languageToLoad) {
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
            context.getResources().getDisplayMetrics());
    }
}