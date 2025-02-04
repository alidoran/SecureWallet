package ir.dorantech.domain.usecase

import ir.dorantech.domain.model.SignInRequest
import ir.dorantech.domain.model.UseCaseResult

interface SignInUseCase {
    suspend operator fun invoke(signInRequest: SignInRequest): UseCaseResult<Unit>
}