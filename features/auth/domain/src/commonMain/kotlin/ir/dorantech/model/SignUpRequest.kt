package ir.dorantech.model

import ir.dorantech.basedomain.model.UseCaseRequest

data class SignUpRequest(
    val username: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val gender: String,
    val image: String,
    val birthDate: String,
): UseCaseRequest
