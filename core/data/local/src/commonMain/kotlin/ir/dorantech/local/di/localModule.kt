package ir.dorantech.local.di

import app.cash.sqldelight.db.SqlDriver
import ir.dorantech.SecureWalletDatabase
import ir.dorantech.local.db.AuthDataSourceLocal
import ir.dorantech.local.db.UserDataSourceLocal
import ir.dorantech.local.db.impl.AuthDataSourceLocalImpl
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

    private val signInLocalDataSourceModule = DI.Module("signinLocalDataSourceModule") {
        bindSingleton<AuthDataSourceLocal> { AuthDataSourceLocalImpl(instance()) }
    }

    private val userLocalDataSourceModule = DI.Module("userLocalDataSourceModule") {
        bindSingleton<UserDataSourceLocal> { UserDataSourceImplLocal(instance()) }
    }

    val localDataModules = DI.Module("localDataModules") {
        import(sqlDriverModule)
        import(secureWalletDatabaseModule)
        import(signInLocalDataSourceModule)
        import(userLocalDataSourceModule)
    }
}