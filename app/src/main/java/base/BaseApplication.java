package base;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import java.util.Locale;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import tools.PrefManager;


public class BaseApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void attachBaseContext(Context newBase) {
        context = newBase;
        updateBaseContextLocale();
        super.attachBaseContext(ViewPumpContextWrapper.wrap(context));
    }

    private void updateBaseContextLocale() {
        String lang = PrefManager.getInstance().isPersianLanguage() ? "fa" : "en";;
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            context = updateResourcesLocale(context, locale);
        else
            context = updateResourcesLocaleLegacy(context, locale);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private Context updateResourcesLocale(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }

    private Context updateResourcesLocaleLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return context;
    }

    public static Context getContext() {
        return context;
    }
}
