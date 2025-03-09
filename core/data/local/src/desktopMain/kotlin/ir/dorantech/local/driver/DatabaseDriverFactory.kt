package ir.dorantech.local.driver

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import ir.dorantech.SecureWalletDatabase
import ir.dorantech.util.PlatformContext

internal actual fun databaseDriverFactory(platformContext: PlatformContext): SqlDriver {
    val driver: SqlDriver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
    SecureWalletDatabase.Schema.create(driver)
    return driver
}
