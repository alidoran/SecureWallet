package ir.dorantech.di.module

import app.cash.sqldelight.db.SqlDriver
import ir.dorantech.local.database.databaseDriverFactory
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

object LocalDataModules {
    private val sqlDriverModule = DI.Module("sqlDriverModule") {
        bindSingleton<SqlDriver> { databaseDriverFactory(instance()) }
    }

    val localDataModules = DI.Module("localDataModules") {
        import(sqlDriverModule)
    }
}
