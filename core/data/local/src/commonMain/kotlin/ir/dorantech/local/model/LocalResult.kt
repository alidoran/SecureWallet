package ir.dorantech.local.model

sealed interface LocalResult<out T> {
    data class Success<out T>(val data: T) : LocalResult<T>
    data class Failure(val error: DataErrorLocal) : LocalResult<Nothing>
}