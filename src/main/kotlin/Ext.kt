package com.kjson

import kotlinx.serialization.json.*

fun JsonElement.toJValue(): JValue? {
    return when (this) {
        is JsonArray -> JArray(this.jsonArray)
        is JsonObject -> JObject(map = this.jsonObject)
        is JsonPrimitive -> JPrimitive(this)
        else -> null
    }
}