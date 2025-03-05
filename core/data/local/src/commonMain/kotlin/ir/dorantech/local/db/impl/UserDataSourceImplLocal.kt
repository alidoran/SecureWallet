package ir.dorantech.local.db.impl

import ir.dorantech.SecureWalletDatabase
import ir.dorantech.local.UserEntity
import ir.dorantech.local.db.UserDataSourceLocal
import ir.dorantech.local.model.DataErrorLocal
import ir.dorantech.local.model.LocalResult

class UserDataSourceImplLocal(
    private val database: SecureWalletDatabase
) : UserDataSourceLocal {
    override suspend fun getUser(id: Int): LocalResult<UserEntity> {
        runCatching {
            val userEntity = database.userDatabaseQueries
                .selectUserById(id.toLong()).executeAsOneOrNull()
            return if (userEntity != null) LocalResult.Success(userEntity)
            else LocalResult.Failure(DataErrorLocal.NotFound)
        }.getOrElse { return LocalResult.Failure(DataErrorLocal.Unknown(it)) }
    }

    override suspend fun insertUser(userEntity: UserEntity): LocalResult<Boolean> {
        runCatching {
            database.userDatabaseQueries.insertUser(
                userEntity.id,
                userEntity.username,
                userEntity.email
            )
            return LocalResult.Success(true)
        }.getOrElse {
            return LocalResult.Failure(DataErrorLocal.Unknown(it))
        }
    }
}