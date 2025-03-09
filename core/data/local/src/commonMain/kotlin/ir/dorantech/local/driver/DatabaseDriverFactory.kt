package ir.dorantech.local.driver

import app.cash.sqldelight.db.SqlDriver
import ir.dorantech.util.PlatformContext

internal expect fun databaseDriverFactory(platformContext: PlatformContext): SqlDriver
