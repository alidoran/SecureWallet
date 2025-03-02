package ir.dorantech.domain.usecase.impl

import ir.dorantech.domain.mapper.toUseCaseRequest
import ir.dorantech.domain.model.SignInRequest
import ir.dorantech.domain.model.UseCaseResult
import ir.dorantech.domain.usecase.SignInUseCase
import ir.dorantech.domain.util.RepoFailureWrapper.toErrorMessage
import ir.dorantech.model.RepoResult
import ir.dorantech.model.SignInDataRequest
import ir.dorantech.repository.SignInRepository

internal class SignInUseCaseImpl(
    private val signInRepository: SignInRepository
) : SignInUseCase {
    override suspend operator fun invoke(signInRequest: SignInRequest): UseCaseResult<Unit> {
        val signInModel = signInRequest.toUseCaseRequest<SignInDataRequest>()
        return when (val result = signInRepository.checkSignIn(signInModel)) {
            is RepoResult.Success -> UseCaseResult.Success(Unit)
            is RepoResult.Failure -> UseCaseResult.Failure(result.toErrorMessage())
        }
    }
}