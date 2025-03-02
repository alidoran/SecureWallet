package ir.dorantech.remote.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import ir.dorantech.remote.api.SignInDataSourceRemote
import ir.dorantech.remote.api.UserRemoteDataSource
import ir.dorantech.remote.api.impl.SignInDataSourceRemoteImpl
import ir.dorantech.remote.api.impl.UserRemoteDataSourceImpl
import ir.dorantech.remote.network.HttpClientProvider
import ir.dorantech.remote.network.provideHttpClientEngineFactory
import ir.dorantech.remote.util.BaseAddress.BASE_URL_LOCAL
import ir.dorantech.remote.util.BaseAddress.BASE_URL_REMOTE
import ir.dorantech.remote.util.GetEndPoint
import ir.dorantech.util.AppConfig
import ir.dorantech.util.WebHostSource.Local
import ir.dorantech.util.WebHostSource.Remote
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance


object RemoteModules {
    private val userRemoteDataSourceModule = DI.Module("userDataSourceModule") {
        bindSingleton<UserRemoteDataSource> { UserRemoteDataSourceImpl(instance(), instance()) }
    }

    private val signInDataSourceRemoteModule = DI.Module("signInDataSourceModule") {
        bindSingleton<SignInDataSourceRemote> { SignInDataSourceRemoteImpl(instance(), instance()) }
    }

    private val clientEngineModule = DI.Module("clientEngineModule") {
        bindSingleton<HttpClientEngineFactory<*>> { provideHttpClientEngineFactory() }
    }

    private val httpClientModule = DI.Module("httpClientModule") {
        bindSingleton<HttpClient> { HttpClientProvider(instance()).invoke() }
    }

    private val webHostModule = DI.Module("webHostModule") {
        when (AppConfig.webHostSource) {
            Remote -> bindSingleton { BASE_URL_REMOTE }
            Local -> bindSingleton { BASE_URL_LOCAL }
        }
    }

    private val getEndPointModule = DI.Module("getEndPointModule") {
        bindSingleton { GetEndPoint(instance()) }
    }

    val remoteDataModules = DI.Module("dataSourceModules") {
        import(clientEngineModule)
        import(httpClientModule)
        import(userRemoteDataSourceModule)
        import(signInDataSourceRemoteModule)
        import(getEndPointModule)
        import(webHostModule)
    }
}