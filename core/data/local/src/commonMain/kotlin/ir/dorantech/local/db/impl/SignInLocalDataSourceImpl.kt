package ir.dorantech.local.db.impl

import ir.dorantech.SecureWalletDatabase
import ir.dorantech.local.db.SignInLocalDataSource
import model.SignInRequest
import model.DataError
import model.DataResult

internal class SignInLocalDataSourceImpl(
    private val database: SecureWalletDatabase
) : SignInLocalDataSource {
    override suspend fun checkSignIn(signInRequest: SignInRequest): DataResult<Unit> {
        kotlin.runCatching {
            val isValidUser = database.authDatabaseQueries
                .isValidUser(signInRequest.username, signInRequest.password).executeAsOneOrNull()
            return if (isValidUser == true) DataResult.Success(Unit)
            else DataResult.Failure(DataError.Unauthorized)
        }.getOrElse { return DataResult.Failure(DataError.Unknown(it)) }
    }
}