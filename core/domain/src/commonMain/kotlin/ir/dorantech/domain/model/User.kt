package ir.dorantech.domain.model

data class User(
    val id: Int,
    val name: String,
    val email: String
) : UseCaseModel<User>