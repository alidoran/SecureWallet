package base;

import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {

    private static BaseApplication context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }

    public static BaseApplication getContext(){
        return context;
    }
}
