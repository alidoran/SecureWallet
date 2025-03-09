package ir.dorantech.local.db.impl

import ir.dorantech.SecureWalletDatabase
import ir.dorantech.local.db.SignUpLocalDataSource
import model.SignUpRequest
import model.DataError
import model.DataResult

internal class SignUpLocalDataSourceImpl(
    private val database: SecureWalletDatabase
) : SignUpLocalDataSource {
    override suspend fun signUp(signUpRequest: SignUpRequest): DataResult<Unit> {
        return try {
            database.authDatabaseQueries.signup(
                userName = signUpRequest.username,
                password = signUpRequest.password,
                firstName = signUpRequest.firstName,
                lastName = signUpRequest.lastName,
                email = signUpRequest.email,
                phoneNumber = signUpRequest.phoneNumber,
                gender = signUpRequest.gender,
                image = signUpRequest.image,
                birthDate = signUpRequest.birthDate,
            )
            DataResult.Success(Unit)
        } catch (e: Exception) {
            DataResult.Failure(DataError.Unknown(e))
        }
    }
}