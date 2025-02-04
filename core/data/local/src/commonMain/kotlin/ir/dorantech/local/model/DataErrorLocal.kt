package ir.dorantech.local.model

sealed interface DataErrorLocal{
    data object Unauthorized : DataErrorLocal
    data object NotFound : DataErrorLocal
    data object SeverDown : DataErrorLocal
    data object TooManyRequest : DataErrorLocal
    data object BadRequest : DataErrorLocal
    data class Unknown(val exception: Throwable = Throwable("Unknown Error")) : DataErrorLocal
}