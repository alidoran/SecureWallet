package ir.dorantech.mapper

import ir.dorantech.basedomain.model.UseCaseRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

inline fun <reified R : UseCaseRequest, reified T : Any> R.toDataModel(): T {
    val json = Json.encodeToString(this)
    return Json.decodeFromString(json)
}