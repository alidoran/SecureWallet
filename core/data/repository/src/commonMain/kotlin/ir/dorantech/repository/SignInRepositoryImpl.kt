package ir.dorantech.repository

import ir.dorantech.mapper.toDataModel
import ir.dorantech.basedomain.model.RepoResult
import ir.dorantech.local.db.SignInLocalDataSource
import ir.dorantech.model.SignInModel
import ir.dorantech.model.SignInRequest
import ir.dorantech.remote.api.SignInRemoteDataSource
import ir.dorantech.util.asSuccess
import ir.dorantech.util.toRepoResult
import model.DataError
import model.DataResult

internal class SignInRepositoryImpl(
    private val signInRemoteSourceRemoteData: SignInRemoteDataSource,
    private val signInLocalDataSource: SignInLocalDataSource,
) : SignInRepository {
    override suspend fun checkSignIn(
        signInRequest: SignInRequest
    ): RepoResult<SignInModel> {
        return checkLocalSignIn(signInRequest) ?: checkRemoteSignIn(signInRequest)
    }

    private suspend fun checkLocalSignIn(signInRequest: SignInRequest):
            RepoResult<SignInModel>? {
        return when (val localDataResult =
            signInLocalDataSource.checkSignIn(signInRequest.toDataModel())) {
            is DataResult.Success -> SignInModel(isValidate = true).asSuccess()
            is DataResult.Failure -> when (localDataResult.error) {
                DataError.NotFound -> null
                else -> localDataResult.toRepoResult()
            }
        }
    }

    private suspend fun checkRemoteSignIn(signInRequest: SignInRequest):
            RepoResult<SignInModel> {
        return when (val response =
            signInRemoteSourceRemoteData.checkSignIn(signInRequest.toDataModel())) {
            is DataResult.Success<*> -> SignInModel(isValidate = true).asSuccess()
            is DataResult.Failure -> response.toRepoResult()
        }
    }
}