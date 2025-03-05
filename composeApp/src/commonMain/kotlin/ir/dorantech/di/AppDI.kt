package ir.dorantech.di

import ir.dorantech.di.AppModules.appModules
import org.kodein.di.DI

val appDI = DI {
    import(CoreModules)
    import(appModules)
    import(featureModules)
}