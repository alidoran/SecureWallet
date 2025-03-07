package ir.dorantech.util

import ir.dorantech.basedomain.model.RepoResult

internal fun <T : Any> T.asSuccess(): RepoResult<T> {
    return RepoResult.Success(this)
}
