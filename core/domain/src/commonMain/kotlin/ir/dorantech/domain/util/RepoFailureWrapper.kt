package ir.dorantech.domain.util

import ir.dorantech.model.RepoResult
import ir.dorantech.model.RepoError

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