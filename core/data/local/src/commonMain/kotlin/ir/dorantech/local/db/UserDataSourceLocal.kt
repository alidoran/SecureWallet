package ir.dorantech.local.db

import ir.dorantech.local.UserEntity
import ir.dorantech.local.model.LocalResult

interface UserDataSourceLocal {
    suspend fun getUser(id: Int): LocalResult<UserEntity>
    suspend fun insertUser(userEntity: UserEntity): LocalResult<Boolean>
}