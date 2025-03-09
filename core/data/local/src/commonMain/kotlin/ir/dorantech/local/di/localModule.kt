package ir.dorantech.local.di

import app.cash.sqldelight.db.SqlDriver
import ir.dorantech.SecureWalletDatabase
import ir.dorantech.local.db.SignInLocalDataSource
import ir.dorantech.local.db.SignUpLocalDataSource
import ir.dorantech.local.db.UserDataSourceLocal
import ir.dorantech.local.db.impl.SignInLocalDataSourceImpl
import ir.dorantech.local.db.impl.SignUpLocalDataSourceImpl
import ir.dorantech.local.db.impl.UserDataSourceImplLocal
import ir.dorantech.local.driver.databaseDriverFactory
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

object LocalModules {
    private val sqlDriverModule = DI.Module("sqlDriverModule") {
        bindSingleton<SqlDriver> { databaseDriverFactory(instance()) }
    }

    private val secureWalletDatabaseModule = DI.Module("secureWalletDatabaseModule") {
        bindSingleton { SecureWalletDatabase(instance()) }
    }

    private val signInLocalDataSourceModule = DI.Module("signInLocalDataSourceModule") {
        bindSingleton<SignInLocalDataSource> { SignInLocalDataSourceImpl(instance()) }
    }
    private val signUpLocalDataSourceModule = DI.Module("signUpLocalDataSourceModule") {
        bindSingleton<SignUpLocalDataSource> { SignUpLocalDataSourceImpl(instance()) }
    }

    private val userLocalDataSourceModule = DI.Module("userLocalDataSourceModule") {
        bindSingleton<UserDataSourceLocal> { UserDataSourceImplLocal(instance()) }
    }

    val localDataModules = DI.Module("localDataModules") {
        import(sqlDriverModule)
        import(secureWalletDatabaseModule)
        import(signInLocalDataSourceModule)
        import(signUpLocalDataSourceModule)
        import(userLocalDataSourceModule)
    }
}