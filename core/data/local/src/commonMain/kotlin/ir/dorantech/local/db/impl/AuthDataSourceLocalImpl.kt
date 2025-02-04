package ir.dorantech.local.db.impl

import ir.dorantech.SecureWalletDatabase
import ir.dorantech.local.db.AuthDataSourceLocal
import ir.dorantech.local.entity.SignInRequestLocal
import ir.dorantech.local.model.DataErrorLocal
import ir.dorantech.local.model.LocalResult

internal class AuthDataSourceLocalImpl(
    private val database: SecureWalletDatabase
) : AuthDataSourceLocal {
    override suspend fun checkSignIn(signInRequest: SignInRequestLocal): LocalResult<Unit> {
        kotlin.runCatching {
            val isValidUser = database.authDatabaseQueries
                .isValidUser(signInRequest.name, signInRequest.password).executeAsOneOrNull()
            return if (isValidUser == true) LocalResult.Success(Unit)
            else LocalResult.Failure(DataErrorLocal.Unauthorized)

        }.getOrElse { return LocalResult.Failure(DataErrorLocal.Unknown(it)) }
    }
}