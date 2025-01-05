package ir.dorantech.util

import ir.dorantech.model.PlatformContext
import platform.UIKit.UIApplication

actual val platformContextProvider: PlatformContext
    get() = PlatformContext(UIApplication.sharedApplication)