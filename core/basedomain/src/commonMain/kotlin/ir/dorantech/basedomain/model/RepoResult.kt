package ir.dorantech.basedomain.model

sealed interface RepoResult<out T> {
    data class Success<out T>(val data: T) : RepoResult<T>
    data class Failure(val error: RepoError) : RepoResult<Nothing>
}