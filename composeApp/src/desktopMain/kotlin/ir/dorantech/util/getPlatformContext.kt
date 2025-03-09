package ir.dorantech.util

import java.awt.Toolkit

actual fun getPlatformContext(): PlatformContext{
    return PlatformContext(Toolkit.getDefaultToolkit())
}