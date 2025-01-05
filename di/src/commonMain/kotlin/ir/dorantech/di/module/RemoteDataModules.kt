package ir.dorantech.di.module

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import ir.dorantech.remote.api.UserService
import ir.dorantech.remote.api.impl.UserServiceImpl
import ir.dorantech.remote.model.BaseUrl
import ir.dorantech.remote.network.HttpClientProvider
import ir.dorantech.remote.network.provideHttpClientEngineFactory
import ir.dorantech.remote.util.Constants.BASE_URL
import org.kodein.di.DI
import org.kodein.di.bindSingleton
import org.kodein.di.instance

object RemoteDataModules{
    private val userServiceModule = DI.Module("userServiceModule") {
        bindSingleton<UserService> { UserServiceImpl(instance(), instance()) }
    }

    private val clientEngineModule = DI.Module("clientEngineModule") {
        bindSingleton<HttpClientEngineFactory<*>> { provideHttpClientEngineFactory()}
    }

    private val httpClientModule = DI.Module("httpClientModule") {
        bindSingleton<HttpClient> { HttpClientProvider(instance()).invoke() }
    }

    private val baseUrlModule = DI.Module("baseUrlModule") {
        bindSingleton<BaseUrl> { BaseUrl(BASE_URL) }
    }

    val remoteDataModules = DI.Module("remoteDataModules") {
        import(userServiceModule)
        import(clientEngineModule)
        import(httpClientModule)
        import(baseUrlModule)
    }
}
