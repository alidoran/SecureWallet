package ir.dorantech.di

import ir.dorantech.viewmodel.SignInViewModel
import ir.dorantech.viewmodel.SignUpViewModel
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

private val signInViewModel = DI.Module("SignInViewModel") {
    bindSingleton<SignInViewModel> { SignInViewModel(instance()) }
}

private val signUpViewModel = DI.Module("SignUpViewModel") {
    bindSingleton<SignUpViewModel> { SignUpViewModel(instance()) }
}

val authUiModule = DI.Module("AuthUiModule") {
    import(signInViewModel)
    import(signUpViewModel)
}
