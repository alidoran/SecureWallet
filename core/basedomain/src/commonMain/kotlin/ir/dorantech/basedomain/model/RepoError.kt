package ir.dorantech.basedomain.model

sealed interface RepoError{
    data object Unauthorized : RepoError
    data object NotFound : RepoError
    data object SeverDown : RepoError
    data object TooManyRequest : RepoError
    data object BadRequest : RepoError
    data class Unknown(val exception: Throwable = Throwable("Unknown Error")) : RepoError
}
