package org.archguard.meta.model

import kotlinx.serialization.json.JsonObject
import org.archguard.meta.base.Element

data class RestApiElement(
    val uri: String,
    val action: String,
    val statusCodes: List<Int>,
    val request: JsonObject? = null,
    val response: List<JsonObject> = listOf(),
) : Element {
    override fun toString(): String {
        return "RestApi(uri='$uri', action='$action', statusCodes=$statusCodes, request=$request, response=$response)"
    }
}