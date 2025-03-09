package ir.dorantech.remote.api.impl

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import ir.dorantech.remote.api.SignInRemoteDataSource
import ir.dorantech.remote.model.Endpoint
import ir.dorantech.remote.network.setJsonBody
import ir.dorantech.remote.util.GetEndPoint
import ir.dorantech.remote.util.toDataResult
import model.DataError
import model.DataResult
import model.SignInRequest

internal class SignInRemoteDataSourceImpl(
    private val getEndpoint: GetEndPoint,
    private val client: HttpClient,
) : SignInRemoteDataSource {
    override suspend fun checkSignIn(signInRequest: SignInRequest): DataResult<Unit> {
        return try {
            client.post(getEndpoint(Endpoint.SignIn))
            { setJsonBody(signInRequest) }.toDataResult()
        } catch (e: Exception) {
            DataResult.Failure(DataError.Unknown(e))
        }
    }
}