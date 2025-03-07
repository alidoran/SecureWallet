package ir.dorantech.di

import ir.dorantech.viewmodel.SignInViewModel
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

private val signInViewModel = DI.Module("SignInViewModel") {
    bindSingleton<SignInViewModel> { SignInViewModel(instance()) }
}

val authUiModule = DI.Module("AuthUiModule") {
    import(signInViewModel)
}
