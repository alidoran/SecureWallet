package ir.dorantech.domain.di

import ir.dorantech.domain.usecase.impl.SignInUseCaseImpl
import ir.dorantech.domain.usecase.impl.UserByIdUseCase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

private val useCaseModule = DI.Module("useCaseModule") {
    bindSingleton<UserByIdUseCase> { UserByIdUseCase(instance()) }
}

private val signInUseCaseModule = DI.Module("signInUseCaseModule") {
    bindSingleton<SignInUseCaseImpl> { SignInUseCaseImpl(instance()) }
}

val useCaseModules = DI.Module("useCaseModules") {
    import(useCaseModule)
    import(signInUseCaseModule)
}