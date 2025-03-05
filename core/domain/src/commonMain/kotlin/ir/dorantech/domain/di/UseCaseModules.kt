package ir.dorantech.domain.di

import ir.dorantech.domain.usecase.impl.UserByIdUseCase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

private val useCaseModule = DI.Module("useCaseModule") {
    bindSingleton<UserByIdUseCase> { UserByIdUseCase(instance()) }
}

val useCaseModules = DI.Module("useCaseModules") {
    import(useCaseModule)
}