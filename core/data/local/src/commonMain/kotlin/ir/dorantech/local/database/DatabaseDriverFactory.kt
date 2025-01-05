package ir.dorantech.local.database

import app.cash.sqldelight.db.SqlDriver
import ir.dorantech.model.PlatformContext

expect fun databaseDriverFactory(platformContext: PlatformContext): SqlDriver
