package com.kjson

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

class JArray(private val value: JsonArray) : JValue {
    private fun getPrimitive(index: Int) = value.getOrNull(index)?.jsonPrimitive

    override fun optInt(index: Int): Int? {
        return getPrimitive(index)?.intOrNull
    }

    override fun getInt(index: Int): Int {
        return optInt(index) ?: throw JException("Unable to get int by index $index")
    }

    override fun optLong(index: Int): Long? {
        return getPrimitive(index)?.longOrNull
    }

    override fun getLong(index: Int): Long {
        return optLong(index) ?: throw JException("Unable to get long by index $index")
    }

    override fun optDouble(index: Int): Double? {
        return getPrimitive(index)?.doubleOrNull
    }

    override fun getDouble(index: Int): Double {
        return optDouble(index) ?: throw JException("Unable to get double by index $index")
    }

    override fun optFloat(index: Int): Float? {
        return getPrimitive(index)?.floatOrNull
    }

    override fun getFloat(index: Int): Float {
        return optFloat(index) ?: throw JException("Unable to get float by index $index")
    }

    override fun optString(index: Int): String? {
        return getPrimitive(index)?.contentOrNull
    }

    override fun getString(index: Int): String {
        return optString(index) ?: throw JException("Unable to get string by index $index")
    }

    override fun optObject(index: Int): JObject? {
        return (value.getOrNull(index) as? JsonObject)?.let {
            JObject(it)
        }
    }

    override fun getObject(index: Int): JObject {
        return optObject(index) ?: throw JException("Unable to get object by index $index")
    }

    override fun get(index: Int): JValue {
        return opt(index) ?: throw JException("Unable to get value by index $index")
    }

    override fun opt(index: Int): JValue? {
        return value.getOrNull(index)?.toJValue()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is JArray) {
            return false
        }

        return other.value == value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return toString(prettyPrint = true)
    }

    fun toString(prettyPrint: Boolean): String {
        return if (prettyPrint) {
            jsonPrettyPrint
        } else {
            jsonNoPrettyPrint
        }.encodeToString(value = value)
    }

    companion object {
        fun parse(string: String): JArray {
            return try {
                JArray(Json.decodeFromString<JsonArray>(string))
            } catch (e: Exception) {
                throw JException(e.message ?: "Failed to parse JSON")
            }
        }

        fun tryParse(string: String): JArray? {
            return try {
                JArray(Json.decodeFromString<JsonArray>(string))
            } catch (e: Exception) {
                return null
            }
        }
    }
}