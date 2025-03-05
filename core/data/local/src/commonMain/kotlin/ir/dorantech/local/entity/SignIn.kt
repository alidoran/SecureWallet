package ir.dorantech.local.entity

import ir.dorantech.local.model.LocalModel
import kotlinx.serialization.Serializable

@Serializable
data class SignInRequestLocal(val username: String, val password: String) : LocalModel