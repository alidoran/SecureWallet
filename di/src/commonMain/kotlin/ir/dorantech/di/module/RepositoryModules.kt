package ir.dorantech.di.module

import ir.dorantech.domain.repository.UserRepository
import ir.dorantech.repository.UserRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

object RepositoryModules {
    private val repositoryModule = DI.Module("repositoryModule") {
        bindSingleton<UserRepository> { UserRepositoryImpl(instance(), instance()) }
    }

    val repositoryModules = DI.Module("repositoryModules") {
        import(repositoryModule)
    }
}