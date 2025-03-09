package ir.dorantech.repository

import ir.dorantech.domain.repository.UserRepository
import ir.dorantech.remote.api.UserRemoteDataSource
import ir.dorantech.local.UserEntity
import ir.dorantech.local.db.UserDataSourceLocal
import ir.dorantech.mapper.toUserModel
import ir.dorantech.basedomain.model.RepoResult
import ir.dorantech.domain.model.UserModel
import ir.dorantech.remote.model.dto.UserRequest
import ir.dorantech.util.asSuccess
import ir.dorantech.util.toRepoResult
import model.DataError
import model.DataResult

internal class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userDataSourceLocal: UserDataSourceLocal,
) : UserRepository {
    override suspend fun getUser(userId: Int): RepoResult<UserModel> {
        return getUserLocal(userId) ?: getUserRemote(userId)
    }

    private suspend fun getUserLocal(userId: Int): RepoResult<UserModel>? {
        return when (val response = userDataSourceLocal.getUser(userId)) {
            is DataResult.Success -> RepoResult.Success(response.data.toUserModel())
            is DataResult.Failure -> when (response.error) {
                DataError.NotFound -> null
                else -> response.toRepoResult()
            }
        }
    }

    private suspend fun getUserRemote(userId: Int): RepoResult<UserModel> {
        return when (val response = userRemoteDataSource.getUser(UserRequest(userId))) {
            is DataResult.Failure -> response.toRepoResult()
            is DataResult.Success -> (response.data.let {
                userDataSourceLocal.insertUser(
                    UserEntity(id = it.id.toLong(), username = it.username, email = it.email)
                )
                UserModel(it.id, it.username, it.email).asSuccess()
            })
        }
    }
}