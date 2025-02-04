package ir.dorantech.di

import ir.dorantech.feature1.viewmodel.UserViewModel
import ir.dorantech.viewmodel.SignInViewModel
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

object ViewModelModules {
    private val viewModelModule = DI.Module("viewModelModule") {
        bindSingleton<UserViewModel> { UserViewModel(instance()) }
    }

    private val SignInViewModel = DI.Module("SignInViewModel") {
        bindSingleton<SignInViewModel> { SignInViewModel(instance()) }
    }

    val viewModelModules = DI.Module("viewModelModules") {
        import(viewModelModule)
        import(SignInViewModel)
    }
}