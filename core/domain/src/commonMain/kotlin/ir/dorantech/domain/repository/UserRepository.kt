package ir.dorantech.domain.repository

import ir.dorantech.domain.model.UserModel
import ir.dorantech.domain.result.DataResult

interface UserRepository {
    suspend fun getUser(userId: String): DataResult<UserModel>
}