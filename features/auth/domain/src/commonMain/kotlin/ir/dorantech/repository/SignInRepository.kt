package ir.dorantech.repository

import ir.dorantech.basedomain.model.RepoResult
import ir.dorantech.model.SignInModel
import ir.dorantech.model.SignInRequest

interface SignInRepository {
    suspend fun checkSignIn(signInRequest: SignInRequest): RepoResult<SignInModel>
}