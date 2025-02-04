package ir.dorantech.util

import ir.dorantech.model.RepoModel
import ir.dorantech.model.RepoResult

internal fun <T : RepoModel<T>> T.asSuccess(): RepoResult<T> {
    return RepoResult.Success(this)
}
