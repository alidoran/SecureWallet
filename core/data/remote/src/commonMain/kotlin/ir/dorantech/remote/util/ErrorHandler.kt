package ir.dorantech.remote.util

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import ir.dorantech.remote.model.DataErrorRemote
import ir.dorantech.remote.model.RemoteResult

internal suspend inline fun <reified T>HttpResponse.toRemoteResult(): RemoteResult<T> {
    return when(this.status){
        HttpStatusCode.OK -> RemoteResult.Success<T>(this.body())
        HttpStatusCode.BadRequest -> RemoteResult.Failure(DataErrorRemote.BadRequest)
        HttpStatusCode.NotFound -> RemoteResult.Failure(DataErrorRemote.NotFound)
        HttpStatusCode.TooManyRequests -> RemoteResult.Failure(DataErrorRemote.TooManyRequest)
        HttpStatusCode.InternalServerError -> RemoteResult.Failure(DataErrorRemote.SeverDown)
        HttpStatusCode.Unauthorized -> RemoteResult.Failure(DataErrorRemote.Unauthorized)
        else -> RemoteResult.Failure(DataErrorRemote.Unknown(Throwable("Unknown Error")))
    }
}