package ir.dorantech.domain.model

import ir.dorantech.basedomain.model.RepoModel

data class UserModel(
    val id: Int,
    val username: String,
    val email: String
): RepoModel