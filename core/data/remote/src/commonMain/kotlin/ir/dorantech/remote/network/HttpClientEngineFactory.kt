package ir.dorantech.remote.network

import io.ktor.client.engine.HttpClientEngineFactory

internal expect fun provideHttpClientEngineFactory(): HttpClientEngineFactory<*>