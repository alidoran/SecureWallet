package ir.dorantech.repository

import ir.dorantech.remote.api.SignInDataSourceRemote
import ir.dorantech.local.db.AuthDataSourceLocal
import ir.dorantech.local.model.DataErrorLocal
import ir.dorantech.local.model.LocalResult
import ir.dorantech.mapper.toLocalModel
import ir.dorantech.mapper.toRemoteModel
import ir.dorantech.basedomain.model.RepoResult
import ir.dorantech.model.SignInModel
import ir.dorantech.model.SignInRequest
import ir.dorantech.remote.model.RemoteResult
import ir.dorantech.util.asSuccess
import ir.dorantech.util.toRepoResult

internal class SignInRepositoryImpl(
    private val signInDataSourceRemote: SignInDataSourceRemote,
    private val authDataSourceLocal: AuthDataSourceLocal,
) : SignInRepository {
    override suspend fun checkSignIn(
        signInRequest: SignInRequest
    ): RepoResult<SignInModel> {
        return checkLocalSignIn(signInRequest) ?: checkRemoteSignIn(signInRequest)
    }

    private suspend fun checkLocalSignIn(signInRequest: SignInRequest):
            RepoResult<SignInModel>? {
        return when (val localDataResult =
            authDataSourceLocal.checkSignIn(signInRequest.toLocalModel())) {
            is LocalResult.Success -> SignInModel(isValidate = true).asSuccess()
            is LocalResult.Failure -> when (localDataResult.error) {
                DataErrorLocal.NotFound -> null
                else -> localDataResult.toRepoResult()
            }
        }
    }

    private suspend fun checkRemoteSignIn(signInRequest: SignInRequest):
            RepoResult<SignInModel> {
        return when (val response =
            signInDataSourceRemote.checkSignIn(signInRequest.toRemoteModel())) {
            is RemoteResult.Success<*> -> SignInModel(isValidate = true).asSuccess()
            is RemoteResult.Failure -> response.toRepoResult()
        }
    }
}