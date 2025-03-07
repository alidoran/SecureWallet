package ir.dorantech.mapper

import ir.dorantech.basedomain.model.UseCaseRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified R : UseCaseRequest, reified T : Any> R.toRemoteModel(): T {
    val json = Json.encodeToString(this)
    return Json.decodeFromString(json)
}

inline fun <reified R : UseCaseRequest, reified T : Any> R.toLocalModel(): T {
    val json = Json.encodeToString(this)
    return Json.decodeFromString(json)
}