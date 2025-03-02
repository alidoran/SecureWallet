package ir.dorantech.repository

import ir.dorantech.model.RepoResult
import ir.dorantech.model.UserModel

interface UserRepository {
    suspend fun getUser(userId: Int): RepoResult<UserModel>
}