package ir.dorantech.remote.network

import io.ktor.client.engine.HttpClientEngineFactory

expect fun provideHttpClientEngineFactory(): HttpClientEngineFactory<*>