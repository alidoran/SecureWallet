package ir.dorantech.repository.impl

import ir.dorantech.remote.api.UserRemoteDataSource
import ir.dorantech.local.UserEntity
import ir.dorantech.local.db.LocalUserDataSource
import ir.dorantech.local.model.DataErrorLocal
import ir.dorantech.local.model.LocalResult
import ir.dorantech.mapper.toUserModel
import ir.dorantech.model.RepoResult
import ir.dorantech.model.UserModel
import ir.dorantech.remote.model.dto.UserRequest
import ir.dorantech.remote.model.RemoteResult
import ir.dorantech.repository.UserRepository
import ir.dorantech.util.asSuccess
import ir.dorantech.util.toRepoResult

internal class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val localUserDataSource: LocalUserDataSource,
) : UserRepository {
    override suspend fun getUser(userId: Int): RepoResult<UserModel> {
        return getUserLocal(userId) ?: getUserRemote(userId)
    }

    private suspend fun getUserLocal(userId: Int): RepoResult<UserModel>? {
        return when (val response = localUserDataSource.getUser(userId)) {
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
                localUserDataSource.insertUser(
                    UserEntity(id = it.id.toLong(), name = it.name, email = it.email)
                )
                UserModel(it.id, it.name, it.email).asSuccess()
            })
        }
    }
}