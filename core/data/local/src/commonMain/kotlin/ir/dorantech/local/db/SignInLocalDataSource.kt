package ir.dorantech.local.db

import model.SignInRequest
import model.DataResult

interface SignInLocalDataSource {
    suspend fun checkSignIn(signInRequest: SignInRequest): DataResult<Unit>
}