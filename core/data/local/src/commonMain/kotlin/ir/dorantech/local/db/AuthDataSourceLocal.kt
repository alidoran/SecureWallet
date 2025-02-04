package ir.dorantech.local.db

import ir.dorantech.local.entity.SignInRequestLocal
import ir.dorantech.local.model.LocalResult

interface AuthDataSourceLocal {
    suspend fun checkSignIn(signInRequest: SignInRequestLocal): LocalResult<Unit>
}