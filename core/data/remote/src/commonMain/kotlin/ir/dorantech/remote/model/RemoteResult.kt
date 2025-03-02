package ir.dorantech.remote.model

sealed interface RemoteResult<out T> {
    data class Success<out T>(val data: T) : RemoteResult<T>
    data class Failure(val error: DataErrorRemote) : RemoteResult<Nothing>
}