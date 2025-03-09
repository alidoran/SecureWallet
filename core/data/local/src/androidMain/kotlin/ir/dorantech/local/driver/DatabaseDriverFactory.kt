package ir.dorantech.local.driver

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import ir.dorantech.SecureWalletDatabase
import ir.dorantech.util.PlatformContext

internal actual fun databaseDriverFactory(platformContext: PlatformContext): SqlDriver {
    val context = platformContext.context
    return AndroidSqliteDriver(SecureWalletDatabase.Schema, context, "SecureWalletDatabase.db")
}