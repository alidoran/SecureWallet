package ir.dorantech.di.module

import ir.dorantech.domain.usecase.UserByIdUseCase
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

object UseCaseModules {
    private val useCaseModule = DI.Module("useCaseModule") {
        bindSingleton<UserByIdUseCase> { UserByIdUseCase(instance()) }
    }

    val useCaseModules = DI.Module("useCaseModules") {
        import(useCaseModule)
    }
}