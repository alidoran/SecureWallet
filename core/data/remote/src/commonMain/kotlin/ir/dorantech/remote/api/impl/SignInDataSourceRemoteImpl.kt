package ir.dorantech.remote.api.impl

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import ir.dorantech.remote.api.SignInDataSourceRemote
import ir.dorantech.remote.model.DataErrorRemote
import ir.dorantech.remote.model.RemoteResult
import ir.dorantech.remote.model.Endpoint
import ir.dorantech.remote.model.dto.SignInDto
import ir.dorantech.remote.model.dto.SignInRequestRemote
import ir.dorantech.remote.network.setJsonBody
import ir.dorantech.remote.util.GetEndPoint
import ir.dorantech.remote.util.toRemoteResult

internal class SignInDataSourceRemoteImpl(
    private val getEndpoint: GetEndPoint,
    private val client: HttpClient,
) : SignInDataSourceRemote {
    override suspend fun checkSignIn(signInRequest: SignInRequestRemote): RemoteResult<SignInDto> {
        return runCatching {
            return client.post(getEndpoint(Endpoint.SignIn))
            { setJsonBody(signInRequest) }.toRemoteResult()
        }.getOrElse { RemoteResult.Failure(DataErrorRemote.Unknown(it)) }
    }
}