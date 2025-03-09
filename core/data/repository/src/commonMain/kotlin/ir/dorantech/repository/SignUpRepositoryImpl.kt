package ir.dorantech.repository

import ir.dorantech.basedomain.model.RepoResult
import ir.dorantech.config.AppConfig
import ir.dorantech.local.db.SignUpLocalDataSource
import ir.dorantech.mapper.toDataModel
import ir.dorantech.model.SignUpModel
import ir.dorantech.model.SignUpRequest
import ir.dorantech.remote.api.SignUpRemoteDataSource
import ir.dorantech.util.asSuccess
import ir.dorantech.util.toRepoResult
import model.DataResult

internal class SignUpRepositoryImpl(
    private val signUpRemoteSourceRemoteData: SignUpRemoteDataSource,
    private val signInLocalDataSource: SignUpLocalDataSource,
) : SignUpRepository {
    override suspend fun signUp(request: SignUpRequest): RepoResult<SignUpModel> {
        return if (AppConfig.OFFLINE_MODE) localSignup(request) else remoteSignup(request)
    }

    private suspend fun localSignup(request: SignUpRequest): RepoResult<SignUpModel> {
        return when (val localDataResult = signInLocalDataSource.signUp(request.toDataModel())) {
            is DataResult.Success -> SignUpModel(isValidated = true).asSuccess()
            is DataResult.Failure -> localDataResult.toRepoResult()
        }
    }

    private suspend fun remoteSignup(request: SignUpRequest): RepoResult<SignUpModel> {
        return when (val response = signUpRemoteSourceRemoteData.signup(request.toDataModel())){
            is DataResult.Success -> SignUpModel(isValidated = true).asSuccess()
            is DataResult.Failure -> response.toRepoResult()
        }
    }
}