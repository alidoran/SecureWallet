package ir.dorantech.di

import ir.dorantech.usecase.SignInUseCase
import ir.dorantech.usecase.impl.SignInUseCaseImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

private val signInUseCaseModule = DI.Module("signInUseCaseModule") {
    bindSingleton<SignInUseCase> { SignInUseCaseImpl(instance()) }
}

val authUseCaseModules = DI.Module("authUseCaseModules") {
    import(signInUseCaseModule)
}
