package ir.dorantech.domain.model

sealed interface UseCaseResult<out T> {
    data class Success<T>(val data: T) : UseCaseResult<T>
    data class Failure(val errorMessage: String) : UseCaseResult<Nothing>
}