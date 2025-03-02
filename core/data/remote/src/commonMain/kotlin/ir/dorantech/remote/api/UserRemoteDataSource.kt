package ir.dorantech.remote.api

import ir.dorantech.remote.model.RemoteResult
import ir.dorantech.remote.model.dto.UserDto
import ir.dorantech.remote.model.dto.UserRequest

interface UserRemoteDataSource {
    suspend fun getUser(userRequest: UserRequest): RemoteResult<UserDto>
}