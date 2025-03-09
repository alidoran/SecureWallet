package ir.dorantech.local.db.impl

import ir.dorantech.SecureWalletDatabase
import ir.dorantech.local.UserEntity
import ir.dorantech.local.db.UserDataSourceLocal
import model.DataError
import model.DataResult

class UserDataSourceImplLocal(
    private val database: SecureWalletDatabase
) : UserDataSourceLocal {
    override suspend fun getUser(id: Int): DataResult<UserEntity> {
        runCatching {
            val userEntity = database.userDatabaseQueries
                .selectUserById(id.toLong()).executeAsOneOrNull()
            return if (userEntity != null) DataResult.Success(userEntity)
            else DataResult.Failure(DataError.NotFound)
        }.getOrElse { return DataResult.Failure(DataError.Unknown(it)) }
    }

    override suspend fun insertUser(userEntity: UserEntity): DataResult<Boolean> {
        runCatching {
            database.userDatabaseQueries.insertUser(
                userEntity.id,
                userEntity.username,
                userEntity.email
            )
            return DataResult.Success(true)
        }.getOrElse {
            return DataResult.Failure(DataError.Unknown(it))
        }
    }
}