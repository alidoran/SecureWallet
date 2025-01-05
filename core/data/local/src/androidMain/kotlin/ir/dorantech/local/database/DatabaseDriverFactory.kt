package ir.dorantech.local.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ir.dorantech.AppDatabase
import ir.dorantech.model.PlatformContext

actual fun databaseDriverFactory(platformContext: PlatformContext): SqlDriver {
    val context = platformContext.context as Context
    return AndroidSqliteDriver(AppDatabase.Schema, context, "UserDatabase.db")
}