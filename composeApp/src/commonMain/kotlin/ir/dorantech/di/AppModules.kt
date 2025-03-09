package ir.dorantech.di

import ir.dorantech.util.PlatformContext
import ir.dorantech.util.getPlatformContext
import org.kodein.di.DI
import org.kodein.di.bindSingleton

object AppModules {
    private val contextModule = DI.Module("contextModule") {
        bindSingleton<PlatformContext> { getPlatformContext() }
    }

    val appModules = DI.Module("appModules") {
        import(contextModule)
    }
}