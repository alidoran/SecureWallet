package ir.dorantech.remote.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class HttpClientProvider(
    private val engineFactory: HttpClientEngineFactory<*>
) {
    operator fun invoke(): HttpClient =
        HttpClient(engineFactory) {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 3000
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = Logger.DEFAULT
            }

            defaultRequest {
                header(HttpHeaders.AcceptEncoding, "identity")
            }

            install(JsonBodyPlugin)
        }
}
