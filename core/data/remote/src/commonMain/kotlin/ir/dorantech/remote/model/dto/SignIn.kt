package ir.dorantech.remote.model.dto

import ir.dorantech.remote.model.RemoteModel
import kotlinx.serialization.Serializable

@Serializable
data class SignInDto(val status: String): RemoteModel<SignInDto>

@Serializable
data class SignInRequestRemote(val userName: String, val password: String): RemoteModel<SignInRequestRemote>