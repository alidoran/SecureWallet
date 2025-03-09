package ir.dorantech.util

import platform.UIKit.UIApplication

actual fun getPlatformContext(): PlatformContext {
    return PlatformContext(UIApplication.sharedApplication)
}