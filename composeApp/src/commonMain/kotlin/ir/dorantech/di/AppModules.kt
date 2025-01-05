package ir.dorantech.di

import ir.dorantech.model.PlatformContext
import ir.dorantech.util.platformContextProvider
import org.kodein.di.DI
import org.kodein.di.bindSingleton

object AppModules {
    private val contextModule = DI.Module("contextModule") {
        bindSingleton<PlatformContext> { platformContextProvider }
    }

    val appModules = DI.Module("appModules") {
        import(contextModule)
    }
}