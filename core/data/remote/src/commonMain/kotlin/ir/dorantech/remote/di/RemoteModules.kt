package ir.dorantech.remote.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import ir.dorantech.config.AppConfig
import ir.dorantech.model.WebHostSource
import ir.dorantech.remote.api.SignInRemoteDataSource
import ir.dorantech.remote.api.SignUpRemoteDataSource
import ir.dorantech.remote.api.UserRemoteDataSource
import ir.dorantech.remote.api.impl.SignInRemoteDataSourceImpl
import ir.dorantech.remote.api.impl.SignUpRemoteDataSourceImpl
import ir.dorantech.remote.api.impl.UserRemoteDataSourceImpl
import ir.dorantech.remote.network.HttpClientProvider
import ir.dorantech.remote.network.provideHttpClientEngineFactory
import ir.dorantech.remote.util.BaseAddress.BASE_URL_LOCAL
import ir.dorantech.remote.util.BaseAddress.BASE_URL_REMOTE
import ir.dorantech.remote.util.GetEndPoint
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance


object RemoteModules {
    private val userRemoteDataSourceModule = DI.Module("userDataSourceModule") {
        bindSingleton<UserRemoteDataSource> { UserRemoteDataSourceImpl(instance(), instance()) }
    }

    private val signInRemoteSourceRemoteDataModule = DI.Module("signInDataSourceModule") {
        bindSingleton<SignInRemoteDataSource> { SignInRemoteDataSourceImpl(instance(), instance()) }
    }

    private val signUpRemoteSourceRemoteDataModule = DI.Module("signUpDataSourceModule") {
        bindSingleton<SignUpRemoteDataSource> { SignUpRemoteDataSourceImpl(instance(), instance()) }
    }

    private val clientEngineModule = DI.Module("clientEngineModule") {
        bindSingleton<HttpClientEngineFactory<*>> { provideHttpClientEngineFactory() }
    }

    private val httpClientModule = DI.Module("httpClientModule") {
        bindSingleton<HttpClient> { HttpClientProvider(instance()).invoke() }
    }

    private val webHostModule = DI.Module("webHostModule") {
        when (AppConfig.WEB_HOST_SOURCE) {
            WebHostSource.Remote -> bindSingleton { BASE_URL_REMOTE }
            WebHostSource.Local -> bindSingleton { BASE_URL_LOCAL }
        }
    }

    private val getEndPointModule = DI.Module("getEndPointModule") {
        bindSingleton { GetEndPoint(instance()) }
    }

    val remoteDataModules = DI.Module("dataSourceModules") {
        import(clientEngineModule)
        import(httpClientModule)
        import(userRemoteDataSourceModule)
        import(signInRemoteSourceRemoteDataModule)
        import(signUpRemoteSourceRemoteDataModule)
        import(getEndPointModule)
        import(webHostModule)
    }
}