package ir.dorantech.di

import ir.dorantech.repository.SignInRepository
import ir.dorantech.repository.UserRepository
import ir.dorantech.repository.impl.SignInRepositoryImpl
import ir.dorantech.repository.impl.UserRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

object RepositoryModules {
    private val userRepositoryModule = DI.Module("repositoryModule") {
        bindSingleton<UserRepository> { UserRepositoryImpl(instance(), instance()) }
    }

    private val signInRepositoryModule = DI.Module("signInRepositoryModule") {
        bindSingleton<SignInRepository> { SignInRepositoryImpl(instance(), instance()) }
    }

    val repositoryModules = DI.Module("repositoryModules") {
        import(userRepositoryModule)
        import(signInRepositoryModule)
    }
}