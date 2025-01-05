package ir.dorantech.util

import ir.dorantech.model.PlatformContext
import java.awt.Toolkit

actual val platformContextProvider: PlatformContext
    get() = PlatformContext(Toolkit.getDefaultToolkit())