package ir.dorantech.remote.api

import ir.dorantech.remote.model.dto.UserDto
import ir.dorantech.remote.model.dto.UserRequest
import model.DataResult

interface UserRemoteDataSource {
    suspend fun getUser(userRequest: UserRequest): DataResult<UserDto>
}