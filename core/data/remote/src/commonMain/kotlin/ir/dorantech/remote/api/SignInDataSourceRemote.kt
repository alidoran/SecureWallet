package ir.dorantech.remote.api

import ir.dorantech.remote.model.RemoteResult
import ir.dorantech.remote.model.dto.SignInDto
import ir.dorantech.remote.model.dto.SignInRequestRemote

interface SignInDataSourceRemote {
    suspend fun checkSignIn(signInRequest: SignInRequestRemote): RemoteResult<SignInDto>
}