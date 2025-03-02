package ir.dorantech.util

import ir.dorantech.local.model.DataErrorLocal
import ir.dorantech.local.model.LocalResult
import ir.dorantech.model.RepoError
import ir.dorantech.model.RepoResult

internal fun LocalResult.Failure.toRepoResult(): RepoResult.Failure {
    return when (this.error) {
        DataErrorLocal.BadRequest -> RepoResult.Failure(RepoError.BadRequest)
        DataErrorLocal.NotFound -> RepoResult.Failure(RepoError.NotFound)
        DataErrorLocal.SeverDown -> RepoResult.Failure(RepoError.SeverDown)
        DataErrorLocal.TooManyRequest -> RepoResult.Failure(RepoError.TooManyRequest)
        DataErrorLocal.Unauthorized -> RepoResult.Failure(RepoError.Unauthorized)
        is DataErrorLocal.Unknown -> RepoResult.Failure(RepoError.Unknown())
    }
}
