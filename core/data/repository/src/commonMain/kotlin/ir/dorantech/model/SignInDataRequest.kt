package ir.dorantech.model

import kotlinx.serialization.Serializable

@Serializable
data class SignInDataRequest(
    val username: String,
    val password: String,
): RepoModel<SignInDataRequest>
