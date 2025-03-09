package ir.dorantech.usecase.impl

import ir.dorantech.basedomain.mapper.RepoFailureWrapper.toErrorMessage
import ir.dorantech.basedomain.model.RepoResult
import ir.dorantech.basedomain.model.UseCaseResult
import ir.dorantech.model.SignUpRequest
import ir.dorantech.repository.SignUpRepository
import ir.dorantech.usecase.SignUpUseCase

internal class SignUpUseCaseImpl(
    private val signUpRepository: SignUpRepository
) : SignUpUseCase {
    override suspend fun invoke(signUpRequest: SignUpRequest): UseCaseResult<Unit> {
        return when (val signUpResult = signUpRepository.signUp(signUpRequest)) {
            is RepoResult.Success -> {
                UseCaseResult.Success(Unit)
            }

            is RepoResult.Failure -> {
                UseCaseResult.Failure(signUpResult.toErrorMessage())
            }
        }
    }
}