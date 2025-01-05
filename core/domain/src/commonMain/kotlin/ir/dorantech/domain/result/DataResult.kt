package ir.dorantech.domain.result

sealed class DataResult<out T> {
    data class Success<out T>(val data: T) : DataResult<T>()
    data class Error(val dataError: DataError) : DataResult<Nothing>()
}

sealed interface DataError{
    data class SeverDown(val exception: Throwable): DataError
    data class Unauthorized(val exception: Throwable): DataError
    data class NotFound(val exception: Throwable): DataError
    data class Unknown(val exception: Throwable): DataError
}