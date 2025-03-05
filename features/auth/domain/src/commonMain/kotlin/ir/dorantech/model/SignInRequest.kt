package ir.dorantech.model

import ir.dorantech.basedomain.model.UseCaseRequest
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val username: String,
    val password: String,
): UseCaseRequest
