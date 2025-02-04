package ir.dorantech.mapper

import ir.dorantech.local.model.LocalModel
import ir.dorantech.model.RepoModel
import ir.dorantech.remote.model.RemoteModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal inline fun <reified T : RemoteModel<Any>>
        RepoModel<*>.toRemoteModel(): T {
    val json = Json.encodeToString(this)
    return Json.decodeFromString(json)
}

internal inline fun <reified T : LocalModel<Any>>
        RepoModel<*>.toLocalModel(): T {
    val json = Json.encodeToString(this)
    return Json.decodeFromString(json)
}