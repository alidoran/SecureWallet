package ir.dorantech.remote.api

import model.DataResult
import model.SignInRequest

interface SignInRemoteDataSource {
    suspend fun checkSignIn(signInRequest: SignInRequest): DataResult<Unit>
}