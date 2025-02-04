package ir.dorantech.util

import ir.dorantech.remote.model.DataErrorRemote
import ir.dorantech.remote.model.RemoteResult
import ir.dorantech.model.RepoError
import ir.dorantech.model.RepoResult

internal fun RemoteResult.Failure.toRepoResult(): RepoResult.Failure {
    return when (this.error) {
        DataErrorRemote.BadRequest -> RepoResult.Failure(RepoError.BadRequest)
        DataErrorRemote.NotFound -> RepoResult.Failure(RepoError.NotFound)
        DataErrorRemote.SeverDown -> RepoResult.Failure(RepoError.SeverDown)
        DataErrorRemote.TooManyRequest -> RepoResult.Failure(RepoError.TooManyRequest)
        DataErrorRemote.Unauthorized -> RepoResult.Failure(RepoError.Unauthorized)
        is DataErrorRemote.Unknown -> RepoResult.Failure(RepoError.Unknown())
    }
}
