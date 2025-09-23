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

fun MutableMap<String, JValue>.put(key: String, value: String) {
    this[key] = JValue.from(value)
}

fun MutableMap<String, JValue>.put(key: String, value: Number) {
    this[key] = JValue.from(value)
}

fun MutableMap<String, JValue>.put(key: String, value: Boolean) {
    this[key] = JValue.from(value)
}

fun MutableMap<String, JValue>.put(key: String, value: Map<String, JValue>) {
    this[key] = JValue.from(value)
}

fun MutableMap<String, JValue>.put(key: String, value: List<JValue>) {
    this[key] = JValue.from(value)
}

fun MutableList<JValue>.add(value: String) {
    this.add(JValue.from(value))
}

fun MutableList<JValue>.add(value: Number) {
    this.add(JValue.from(value))
}

fun MutableList<JValue>.add(value: Boolean) {
    this.add(JValue.from(value))
}

fun MutableList<JValue>.add(value: Map<String, JValue>) {
    this.add(JValue.from(value))
}

fun MutableList<JValue>.add(value: List<JValue>) {
    this.add(JValue.from(value))
}

