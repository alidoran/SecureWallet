package ir.dorantech.repository

import ir.dorantech.domain.repository.UserRepository
import ir.dorantech.remote.api.UserRemoteDataSource
import ir.dorantech.local.UserEntity
import ir.dorantech.local.db.UserDataSourceLocal
import ir.dorantech.local.model.DataErrorLocal
import ir.dorantech.local.model.LocalResult
import ir.dorantech.mapper.toUserModel
import ir.dorantech.basedomain.model.RepoResult
import ir.dorantech.domain.model.UserModel
import ir.dorantech.remote.model.dto.UserRequest
import ir.dorantech.remote.model.RemoteResult
import ir.dorantech.util.asSuccess
import ir.dorantech.util.toRepoResult

internal class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userDataSourceLocal: UserDataSourceLocal,
) : UserRepository {
    override suspend fun getUser(userId: Int): RepoResult<UserModel> {
        return getUserLocal(userId) ?: getUserRemote(userId)
    }

    private suspend fun getUserLocal(userId: Int): RepoResult<UserModel>? {
        return when (val response = userDataSourceLocal.getUser(userId)) {
            is LocalResult.Success -> RepoResult.Success(response.data.toUserModel())
            is LocalResult.Failure -> when (response.error) {
                DataErrorLocal.NotFound -> null
                else -> response.toRepoResult()
            }
        }
    }

    private suspend fun getUserRemote(userId: Int): RepoResult<UserModel> {
        return when (val response = userRemoteDataSource.getUser(UserRequest(userId))) {
            is RemoteResult.Failure -> response.toRepoResult()
            is RemoteResult.Success -> (response.data.let {
                userDataSourceLocal.insertUser(
                    UserEntity(id = it.id.toLong(), username = it.username, email = it.email)
                )
                UserModel(it.id, it.username, it.email).asSuccess()
            })
        }
    }
}