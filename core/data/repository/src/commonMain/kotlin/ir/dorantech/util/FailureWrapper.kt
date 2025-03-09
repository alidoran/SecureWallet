package ir.dorantech.util

import ir.dorantech.basedomain.model.RepoError
import ir.dorantech.basedomain.model.RepoResult
import model.DataError
import model.DataResult

internal fun DataResult.Failure.toRepoResult(): RepoResult.Failure {
    return when (this.error) {
        DataError.BadRequest -> RepoResult.Failure(RepoError.BadRequest)
        DataError.NotFound -> RepoResult.Failure(RepoError.NotFound)
        DataError.SeverDown -> RepoResult.Failure(RepoError.SeverDown)
        DataError.TooManyRequest -> RepoResult.Failure(RepoError.TooManyRequest)
        DataError.Unauthorized -> RepoResult.Failure(RepoError.Unauthorized)
        is DataError.Unknown -> RepoResult.Failure(RepoError.Unknown())
    }
}
