package ir.dorantech.repository

import app.cash.sqldelight.db.SqlDriver
import io.ktor.client.call.*
import io.ktor.http.*
import ir.dorantech.AppDatabase
import ir.dorantech.domain.model.UserModel
import ir.dorantech.domain.repository.UserRepository
import ir.dorantech.domain.result.DataError
import ir.dorantech.domain.result.DataResult
import ir.dorantech.mapper.toDomain
import ir.dorantech.remote.api.UserService
import ir.dorantech.remote.dto.UserDto
import ir.dorantech.remote.dto.UserRequest

class UserRepositoryImpl(
    private val userService: UserService,
    sqlDriver: SqlDriver,
) : UserRepository {

    private val database = AppDatabase(sqlDriver)

    override suspend fun getUser(userId: String): DataResult<UserModel> {
        val localUser = database.userDatabaseQueries.selectUserById(userId).executeAsOneOrNull()

        return if (localUser != null)
            DataResult.Success(UserDto(localUser.id, localUser.name, localUser.email).toDomain())
        else {
            val response = userService.getUser(UserRequest(userId))

            when (response.status) {
                HttpStatusCode.OK -> {
                    val userDto = response.body<UserDto>()
                    database.userDatabaseQueries.insertUser(userDto.id, userDto.name, userDto.email)
                    DataResult.Success(userDto.toDomain())
                }

                HttpStatusCode.Unauthorized ->
                    DataResult.Error(DataError.Unauthorized(response.body<Throwable>()))

                else -> DataResult.Error(DataError.Unknown(response.body<Throwable>()))

            }
        }
    }
}