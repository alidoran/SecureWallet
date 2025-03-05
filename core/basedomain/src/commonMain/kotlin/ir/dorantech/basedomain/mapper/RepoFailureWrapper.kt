package ir.dorantech.basedomain.mapper

import ir.dorantech.basedomain.model.RepoError
import ir.dorantech.basedomain.model.RepoResult

object RepoFailureWrapper {
    fun RepoResult.Failure.toErrorMessage() =
        when (this.error) {
            RepoError.BadRequest -> "Check input data"
            RepoError.NotFound -> "Not found"
            RepoError.SeverDown -> "Not response, try later"
            RepoError.TooManyRequest -> "Three failure, wait two minutes"
            RepoError.Unauthorized -> "Don't have access"
            is RepoError.Unknown -> "Unknown error"
        }
}