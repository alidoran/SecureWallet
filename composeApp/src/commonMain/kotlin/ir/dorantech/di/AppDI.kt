package ir.dorantech.di

import ir.dorantech.di.AppModules.appModules
import ir.dorantech.di.ViewModelModules.viewModelModules
import org.kodein.di.DI

val appDI = DI {
    import(CoreModules)
    import(viewModelModules)
    import(appModules)
}