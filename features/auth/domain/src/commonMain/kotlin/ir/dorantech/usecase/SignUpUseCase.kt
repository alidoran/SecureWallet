package ir.dorantech.usecase

import ir.dorantech.basedomain.model.UseCaseResult
import ir.dorantech.model.SignUpRequest

interface SignUpUseCase {
    suspend operator fun invoke(signUpRequest: SignUpRequest) : UseCaseResult<Unit>
}