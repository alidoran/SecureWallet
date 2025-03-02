package ir.dorantech.remote.model.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Int,
    val name: String,
    val email: String
)

@Serializable
data class UserRequest(val id: Int)