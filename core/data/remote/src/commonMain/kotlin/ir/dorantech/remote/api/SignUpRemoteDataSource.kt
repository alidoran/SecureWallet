package ir.dorantech.remote.api

import model.DataResult
import model.SignUpRequest

interface SignUpRemoteDataSource {
    suspend fun signup(signUpRequest: SignUpRequest): DataResult<Unit>
}