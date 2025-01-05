package ir.dorantech.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val name: String,
    val email: String
)

@Serializable
data class UserRequest(val id: String)