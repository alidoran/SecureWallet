package ir.dorantech.di

import ir.dorantech.feature1.viewmodel.UserViewModel
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

object ViewModelModules {
    private val viewModelModule = DI.Module("viewModelModule") {
        bindSingleton<UserViewModel> { UserViewModel(instance()) }
    }

    val viewModelModules = DI.Module("viewModelModules") {
        import(viewModelModule)
    }
}