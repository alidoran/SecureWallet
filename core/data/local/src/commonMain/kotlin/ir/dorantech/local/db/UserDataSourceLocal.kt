package ir.dorantech.local.db

import ir.dorantech.local.UserEntity
import model.DataResult

interface UserDataSourceLocal {
    suspend fun getUser(id: Int): DataResult<UserEntity>
    suspend fun insertUser(userEntity: UserEntity): DataResult<Boolean>
}