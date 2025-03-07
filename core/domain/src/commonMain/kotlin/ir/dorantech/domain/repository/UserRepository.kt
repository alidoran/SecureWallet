package ir.dorantech.domain.repository

import ir.dorantech.basedomain.model.RepoResult
import ir.dorantech.domain.model.UserModel

interface UserRepository {
    suspend fun getUser(userId: Int): RepoResult<UserModel>
}