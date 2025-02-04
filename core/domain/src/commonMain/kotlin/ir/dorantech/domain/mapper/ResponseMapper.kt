package ir.dorantech.domain.mapper

import ir.dorantech.domain.model.UseCaseModel
import ir.dorantech.model.RepoResult
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal inline fun <reified T : UseCaseModel<Any>>
        RepoResult<*>.toUseCaseResult(): T {
    val json = Json.encodeToString(this)
    return Json.decodeFromString(json)
}
