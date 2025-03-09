package ir.dorantech.remote.api.impl

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import ir.dorantech.remote.api.SignUpRemoteDataSource
import ir.dorantech.remote.model.Endpoint
import ir.dorantech.remote.network.setJsonBody
import ir.dorantech.remote.util.GetEndPoint
import ir.dorantech.remote.util.toDataResult
import model.DataError
import model.DataResult
import model.SignUpRequest

internal class SignUpRemoteDataSourceImpl(
    private val getEndpoint: GetEndPoint,
    private val client: HttpClient,
): SignUpRemoteDataSource{
    override suspend fun signup(signUpRequest: SignUpRequest): DataResult<Unit> {
        return try {
            client.post(getEndpoint(Endpoint.SignUp))
            { setJsonBody(signUpRequest)}.toDataResult()
        }catch (e: Exception){
            DataResult.Failure(DataError.Unknown(e))
        }
    }
}