package ir.dorantech.domain.model

data class SignInRequest(
    val username: String,
    val password: String,
): UseCaseModel<SignInRequest>
