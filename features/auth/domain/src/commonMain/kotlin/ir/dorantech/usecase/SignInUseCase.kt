package ir.dorantech.usecase

import ir.dorantech.basedomain.model.UseCaseResult
import ir.dorantech.model.SignInRequest

interface SignInUseCase {
    suspend operator fun invoke(signInRequest: SignInRequest): UseCaseResult<Unit>
}