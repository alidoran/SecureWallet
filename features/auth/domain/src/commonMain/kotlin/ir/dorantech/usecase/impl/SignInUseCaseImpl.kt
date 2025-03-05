package ir.dorantech.usecase.impl

import ir.dorantech.basedomain.mapper.RepoFailureWrapper.toErrorMessage
import ir.dorantech.basedomain.model.RepoResult
import ir.dorantech.basedomain.model.UseCaseResult
import ir.dorantech.model.SignInRequest
import ir.dorantech.repository.SignInRepository
import ir.dorantech.usecase.SignInUseCase

internal class SignInUseCaseImpl(
    private val signInRepository: SignInRepository
) : SignInUseCase {
    override suspend operator fun invoke(signInRequest: SignInRequest): UseCaseResult<Unit> {
        return when (val result = signInRepository.checkSignIn(signInRequest)) {
            is RepoResult.Success -> UseCaseResult.Success(Unit)
            is RepoResult.Failure -> UseCaseResult.Failure(result.toErrorMessage())
        }
    }
}