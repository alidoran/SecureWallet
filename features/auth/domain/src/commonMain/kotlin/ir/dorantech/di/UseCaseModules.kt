package ir.dorantech.di

import ir.dorantech.usecase.SignInUseCase
import ir.dorantech.usecase.SignUpUseCase
import ir.dorantech.usecase.impl.SignInUseCaseImpl
import ir.dorantech.usecase.impl.SignUpUseCaseImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

private val signInUseCaseModule = DI.Module("signInUseCaseModule") {
    bindSingleton<SignInUseCase> { SignInUseCaseImpl(instance()) }
}

private val signUpUseCaseModule = DI.Module("signUpUseCaseModule") {
    bindSingleton<SignUpUseCase> { SignUpUseCaseImpl(instance()) }
}

val authUseCaseModules = DI.Module("authUseCaseModules") {
    import(signInUseCaseModule)
    import(signUpUseCaseModule)
}
