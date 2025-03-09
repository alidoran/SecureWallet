package model

sealed interface DataError{
    data object Unauthorized : DataError
    data object NotFound : DataError
    data object SeverDown : DataError
    data object TooManyRequest : DataError
    data object BadRequest : DataError
    data class Unknown(val exception: Throwable = Throwable("Unknown Error")) : DataError
}