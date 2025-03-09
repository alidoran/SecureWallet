package ir.dorantech.remote.api.impl

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import ir.dorantech.remote.api.UserRemoteDataSource
import ir.dorantech.remote.model.Endpoint.*
import ir.dorantech.remote.model.dto.UserDto
import ir.dorantech.remote.model.dto.UserRequest
import ir.dorantech.remote.network.setJsonBody
import ir.dorantech.remote.util.GetEndPoint
import ir.dorantech.remote.util.toDataResult
import model.DataError
import model.DataResult

internal class UserRemoteDataSourceImpl(
    private val getEndPoint: GetEndPoint,
    private val client: HttpClient
) : UserRemoteDataSource {
    override suspend fun getUser(userRequest: UserRequest): DataResult<UserDto> {
        runCatching {
            return client.post(getEndPoint(User))
            { setJsonBody(userRequest) }.toDataResult()
        }.getOrElse { return DataResult.Failure(DataError.Unknown(it)) }
    }
}