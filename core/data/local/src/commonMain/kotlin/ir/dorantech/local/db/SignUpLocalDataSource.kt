package ir.dorantech.local.db

import model.SignUpRequest
import model.DataResult

interface SignUpLocalDataSource {
    suspend fun signUp(signUpRequest: SignUpRequest): DataResult<Unit>
}