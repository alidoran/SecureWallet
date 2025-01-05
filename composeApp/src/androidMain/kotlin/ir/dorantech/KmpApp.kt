package ir.dorantech

import android.app.Application
import ir.dorantech.util.appContext

class KmpApp : Application(){
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}