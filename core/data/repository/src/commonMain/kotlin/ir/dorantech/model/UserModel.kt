package ir.dorantech.model

data class UserModel(
    val id: Int,
    val name: String,
    val email: String
): RepoModel<UserModel>