package ir.dorantech.repository

import ir.dorantech.model.RepoResult
import ir.dorantech.model.SignInDataRequest
import ir.dorantech.model.SignInModel

interface SignInRepository {
    suspend fun checkSignIn(signInDataRequest: SignInDataRequest): RepoResult<SignInModel>
}