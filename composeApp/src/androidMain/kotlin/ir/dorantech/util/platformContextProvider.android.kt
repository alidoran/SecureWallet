package ir.dorantech.util

import android.content.Context
import ir.dorantech.model.PlatformContext

lateinit var appContext: Context

actual val platformContextProvider: PlatformContext
    get() = PlatformContext(appContext)
