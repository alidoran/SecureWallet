package ir.dorantech.remote.model

sealed interface DataErrorRemote{
    data object Unauthorized : DataErrorRemote
    data object NotFound : DataErrorRemote
    data object SeverDown : DataErrorRemote
    data object TooManyRequest : DataErrorRemote
    data object BadRequest : DataErrorRemote
    data class Unknown(val exception: Throwable) : DataErrorRemote
}