package ir.dorantech.repository.impl

import ir.dorantech.remote.api.SignInDataSourceRemote
import ir.dorantech.local.db.AuthDataSourceLocal
import ir.dorantech.local.entity.SignInRequestLocal
import ir.dorantech.local.model.DataErrorLocal
import ir.dorantech.local.model.LocalResult
import ir.dorantech.mapper.toLocalModel
import ir.dorantech.mapper.toRemoteModel
import ir.dorantech.model.RepoResult
import ir.dorantech.model.SignInDataRequest
import ir.dorantech.model.SignInModel
import ir.dorantech.remote.model.dto.SignInRequestRemote
import ir.dorantech.remote.model.RemoteResult
import ir.dorantech.repository.SignInRepository
import ir.dorantech.util.asSuccess
import ir.dorantech.util.toRepoResult

internal class SignInRepositoryImpl(
    private val signInDataSourceRemote: SignInDataSourceRemote,
    private val authDataSourceLocal: AuthDataSourceLocal,
) : SignInRepository {
    override suspend fun checkSignIn(
        signInDataRequest: SignInDataRequest
    ): RepoResult<SignInModel> {
        return checkLocalSignIn(signInDataRequest) ?: checkRemoteSignIn(signInDataRequest)
    }

    private suspend fun checkLocalSignIn(signInDataRequest: SignInDataRequest):
            RepoResult<SignInModel>? {
        return when (val localDataResult =
            authDataSourceLocal.checkSignIn(signInDataRequest.toLocalModel<SignInRequestLocal>())) {
            is LocalResult.Success -> SignInModel(isValidate = true).asSuccess()
            is LocalResult.Failure -> when (localDataResult.error) {
                DataErrorLocal.NotFound -> null
                else -> localDataResult.toRepoResult()
            }
        }
    }

    private suspend fun checkRemoteSignIn(signInDataRequest: SignInDataRequest):
            RepoResult<SignInModel> {
        return when (val response =
            signInDataSourceRemote.checkSignIn(signInDataRequest.toRemoteModel<SignInRequestRemote>())) {
            is RemoteResult.Success<*> -> SignInModel(isValidate = true).asSuccess()
            is RemoteResult.Failure -> response.toRepoResult()
        }
    }
}