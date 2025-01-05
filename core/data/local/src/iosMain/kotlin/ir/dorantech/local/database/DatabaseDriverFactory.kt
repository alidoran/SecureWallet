package ir.dorantech.local.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import ir.dorantech.AppDatabase
import ir.dorantech.model.PlatformContext

actual fun databaseDriverFactory(platformContext: PlatformContext): SqlDriver =
    NativeSqliteDriver(AppDatabase.Schema, "UserDatabase.db")
