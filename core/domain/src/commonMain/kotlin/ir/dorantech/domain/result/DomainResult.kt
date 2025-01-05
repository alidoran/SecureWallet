package ir.dorantech.domain.result

sealed class DomainResult<out T> {
    data class Success<out T>(val data: T) : DomainResult<T>()
    data class Failure(val error: DomainError) : DomainResult<Nothing>()
}

sealed class DomainError {
    data object InvalidInput : DomainError()
    data object Unauthorized : DomainError()
    data object TryLater : DomainError()
    data class Unknown(val message: String) : DomainError()
}