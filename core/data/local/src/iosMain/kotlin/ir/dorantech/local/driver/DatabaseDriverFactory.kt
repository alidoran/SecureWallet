package ir.dorantech.local.driver

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import ir.dorantech.SecureWalletDatabase
import ir.dorantech.model.PlatformContext

internal actual fun databaseDriverFactory(platformContext: PlatformContext): SqlDriver =
    NativeSqliteDriver(SecureWalletDatabase.Schema, "SecureWalletDatabase.db")