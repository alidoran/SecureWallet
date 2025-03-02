package ir.dorantech.remote.api.impl

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import ir.dorantech.remote.api.UserRemoteDataSource
import ir.dorantech.remote.model.DataErrorRemote
import ir.dorantech.remote.model.RemoteResult
import ir.dorantech.remote.model.Endpoint.*
import ir.dorantech.remote.model.dto.UserDto
import ir.dorantech.remote.model.dto.UserRequest
import ir.dorantech.remote.network.setJsonBody
import ir.dorantech.remote.util.GetEndPoint
import ir.dorantech.remote.util.toRemoteResult

internal class UserRemoteDataSourceImpl(
    private val getEndPoint: GetEndPoint,
    private val client: HttpClient
) : UserRemoteDataSource {
    override suspend fun getUser(userRequest: UserRequest): RemoteResult<UserDto> {
        runCatching {
            return client.post(getEndPoint(User))
            { setJsonBody(userRequest) }.toRemoteResult()
        }.getOrElse { return RemoteResult.Failure(DataErrorRemote.Unknown(it)) }
    }
}