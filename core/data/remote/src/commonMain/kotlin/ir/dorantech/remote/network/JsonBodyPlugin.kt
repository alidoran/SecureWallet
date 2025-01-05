package ir.dorantech.remote.network

import io.ktor.client.HttpClient
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.util.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import io.ktor.http.*
import io.ktor.http.content.OutgoingContent
import io.ktor.utils.io.InternalAPI

class JsonBodyPlugin private constructor() {

    class Config

    companion object Plugin : HttpClientPlugin<Config, JsonBodyPlugin> {
        override val key: AttributeKey<JsonBodyPlugin> = AttributeKey("JsonBodyPlugin")

        override fun prepare(block: Config.() -> Unit): JsonBodyPlugin = JsonBodyPlugin()

        override fun install(plugin: JsonBodyPlugin, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Transform) { context ->
                if (context is HttpRequestBuilder && context.body is OutgoingContent.NoContent) {
                    if (context.headers[HttpHeaders.ContentType] == null) {
                        context.headers[HttpHeaders.ContentType] = ContentType.Application.Json.toString()
                    }
                }
            }
        }
    }
}

@OptIn(InternalAPI::class)
inline fun <reified T> HttpRequestBuilder.setJsonBody(data: T) {
    this.body = Json.encodeToString(data)
    this.contentType(ContentType.Application.Json)
}
