package ir.dorantech.domain.mapper


import ir.dorantech.basedomain.model.RepoResult
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal inline fun <reified T : Any>
        RepoResult<*>.toUseCaseResult(): T {
    val json = Json.encodeToString(this)
    return Json.decodeFromString(json)
}
