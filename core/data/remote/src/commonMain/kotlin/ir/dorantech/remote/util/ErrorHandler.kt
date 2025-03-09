package ir.dorantech.remote.util

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import model.DataError
import model.DataResult

internal suspend inline fun <reified T>HttpResponse.toDataResult(): DataResult<T> {
    return when(this.status){
        HttpStatusCode.OK -> DataResult.Success<T>(this.body())
        HttpStatusCode.BadRequest -> DataResult.Failure(DataError.BadRequest)
        HttpStatusCode.NotFound -> DataResult.Failure(DataError.NotFound)
        HttpStatusCode.TooManyRequests -> DataResult.Failure(DataError.TooManyRequest)
        HttpStatusCode.InternalServerError -> DataResult.Failure(DataError.SeverDown)
        HttpStatusCode.Unauthorized -> DataResult.Failure(DataError.Unauthorized)
        else -> DataResult.Failure(DataError.Unknown(Throwable("Unknown Error")))
    }
}