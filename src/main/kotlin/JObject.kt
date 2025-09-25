package com.kjson

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

class JObject(internal val map: JsonObject): JValue {
    override fun optInt(key: String): Int? {
        return map[key]?.jsonPrimitive?.intOrNull
    }

    override fun getInt(key: String): Int {
        return optInt(key) ?: throw JException("$key Int not found")
    }

    override fun optLong(key: String): Long? {
        return map[key]?.jsonPrimitive?.longOrNull
    }

    override fun getLong(key: String): Long {
        return optLong(key) ?: throw JException("$key Long not found")
    }

    override fun optDouble(key: String): Double? {
        return map[key]?.jsonPrimitive?.doubleOrNull
    }

    override fun getDouble(key: String): Double {
        return optDouble(key) ?: throw JException("$key Double not found")
    }

    override fun optFloat(key: String): Float? {
        return map[key]?.jsonPrimitive?.floatOrNull
    }

    override fun getFloat(key: String): Float {
        return optFloat(key) ?: throw JException("$key Float not found")
    }

    override fun optString(key: String): String? {
        return map[key]?.jsonPrimitive?.contentOrNull
    }

    override fun getString(key: String): String {
        return optString(key) ?: throw JException("$key String not found")
    }

    override fun optObject(key: String): JObject? {
        return (map[key] as? JsonObject)?.let {
            JObject(it)
        }
    }

    override fun optArray(key: String): JArray? {
        return (map[key] as? JsonArray)?.let {
            JArray(it)
        }
    }

    override fun getArray(key: String): JArray {
        return optArray(key) ?: throw JException("$key Array not found")
    }

    override fun getObject(key: String): JObject {
        return optObject(key) ?: throw JException("$key Object not found")
    }

    override fun get(key: String): JValue {
        return opt(key) ?: throw JException("$key value not found")
    }

    override fun opt(key: String): JValue? {
        return map[key]?.toJValue()
    }

    override fun hashCode(): Int {
        return map.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is JObject) {
            return false
        }

        return other.map == map
    }

    override fun toString(): String {
        return toString(prettyPrint = true)
    }

    fun toString(prettyPrint: Boolean): String {
        return if (prettyPrint) {
            jsonPrettyPrint
        } else {
            jsonNoPrettyPrint
        }.encodeToString(value = map)
    }

    override fun toMap(): Map<String, JValue> {
        return map.mapValues {
            it.value.toJValue() ?: throw JException("Corrupted JObject")
        }
    }

    override fun toMapOrNull(): Map<String, JValue>? {
        return toMap()
    }

    companion object {
        fun parse(string: String): JObject {
            return try {
                JObject(map = Json.decodeFromString<JsonObject>(string))
            } catch (e: Exception) {
                throw JException(e.message ?: "Failed to parse JSON")
            }
        }

        fun tryParse(string: String): JObject? {
            return try {
                JObject(map = Json.decodeFromString<JsonObject>(string))
            } catch (e: Exception) {
                return null
            }
        }

        fun fromMap(map: Map<String, JValue>): JObject {
            return JObject(
                map = buildJsonObject {
                    map.forEach { (key, value) ->
                        put(key, when(value) {
                            is JObject -> value.map
                            is JArray -> value.value
                            is JPrimitive -> value.value ?: JsonNull
                            else -> JsonNull
                        })
                    }
                }
            )
        }

        fun fromStringMap(map: Map<String, String>): JObject {
            return JObject(
                map = buildJsonObject {
                    map.forEach { (key, value) ->
                        put(key, JsonPrimitive(value))
                    }
                }
            )
        }
    }
}