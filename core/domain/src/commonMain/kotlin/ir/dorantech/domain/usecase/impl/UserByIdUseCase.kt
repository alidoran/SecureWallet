package ir.dorantech.domain.usecase.impl

import ir.dorantech.domain.mapper.toUseCaseResult
import ir.dorantech.domain.model.UseCaseResult
import ir.dorantech.domain.model.User
import ir.dorantech.domain.util.RepoFailureWrapper.toErrorMessage
import ir.dorantech.model.RepoResult
import ir.dorantech.repository.UserRepository

class UserByIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: Int) =
        when (val result = userRepository.getUser(userId)) {
            is RepoResult.Success -> UseCaseResult.Success(result.toUseCaseResult<User>())
            is RepoResult.Failure -> UseCaseResult.Failure(result.toErrorMessage())
        }
}