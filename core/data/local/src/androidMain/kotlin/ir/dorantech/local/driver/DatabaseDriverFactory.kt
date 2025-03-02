package ir.dorantech.local.driver

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ir.dorantech.SecureWalletDatabase
import ir.dorantech.model.PlatformContext

internal actual fun databaseDriverFactory(platformContext: PlatformContext): SqlDriver {
    val context = platformContext.context as Context
    return AndroidSqliteDriver(SecureWalletDatabase.Schema, context, "SecureWalletDatabase.db")
}