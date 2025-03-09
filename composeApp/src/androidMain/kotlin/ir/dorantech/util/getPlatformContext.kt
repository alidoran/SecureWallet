package ir.dorantech.util

import android.content.Context

lateinit var appContext: Context

actual fun getPlatformContext(): PlatformContext {
    return PlatformContext(appContext)
}