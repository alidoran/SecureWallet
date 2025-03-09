package ir.dorantech.repository

import ir.dorantech.basedomain.model.RepoResult
import ir.dorantech.model.SignUpModel
import ir.dorantech.model.SignUpRequest

interface SignUpRepository {
    suspend fun signUp(request: SignUpRequest): RepoResult<SignUpModel>
}