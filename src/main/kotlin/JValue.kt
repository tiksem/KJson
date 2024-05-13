package com.kjson

import kotlinx.serialization.json.Json

internal val jsonPrettyPrint = Json {
    prettyPrint = true
}

internal val jsonNoPrettyPrint = Json {
    prettyPrint = false
}

interface JValue {
    fun optInt(key: String): Int? {
        throw JException("Unable [$key] ${this.javaClass.simpleName} is not and object")
    }
    fun getInt(key: String): Int {
        throw JException("Unable [$key] ${this.javaClass.simpleName} is not and object")
    }
    fun optLong(key: String): Long? {
        throw JException("Unable [$key] ${this.javaClass.simpleName} is not and object")
    }
    fun getLong(key: String): Long {
        throw JException("Unable [$key] ${this.javaClass.simpleName} is not and object")
    }
    fun optDouble(key: String): Double? {
        throw JException("Unable [$key] ${this.javaClass.simpleName} is not and object")
    }
    fun getDouble(key: String): Double {
        throw JException("Unable [$key] ${this.javaClass.simpleName} is not and object")
    }
    fun optFloat(key: String): Float? {
        throw JException("Unable [$key] ${this.javaClass.simpleName} is not and object")
    }
    fun getFloat(key: String): Float {
        throw JException("Unable [$key] ${this.javaClass.simpleName} is not and object")
    }
    fun optString(key: String): String? {
        throw JException("Unable [$key] ${this.javaClass.simpleName} is not and object")
    }
    fun getString(key: String): String {
        throw JException("Unable [$key] ${this.javaClass.simpleName} is not and object")
    }
    fun optObject(key: String): JObject? {
        throw JException("Unable [$key] ${this.javaClass.simpleName} is not and object")
    }
    fun getObject(key: String): JObject {
        throw JException("Unable [$key] ${this.javaClass.simpleName} is not and object")
    }

    fun optInt(index: Int): Int? {
        throw JException("Unable [$index] ${this.javaClass.simpleName} is not and object")
    }
    fun getInt(index: Int): Int {
        throw JException("Unable [$index] ${this.javaClass.simpleName} is not and object")
    }
    fun optLong(index: Int): Long? {
        throw JException("Unable [$index] ${this.javaClass.simpleName} is not and object")
    }
    fun getLong(index: Int): Long {
        throw JException("Unable [$index] ${this.javaClass.simpleName} is not and object")
    }
    fun optDouble(index: Int): Double? {
        throw JException("Unable [$index] ${this.javaClass.simpleName} is not and object")
    }
    fun getDouble(index: Int): Double {
        throw JException("Unable [$index] ${this.javaClass.simpleName} is not and object")
    }
    fun optFloat(index: Int): Float? {
        throw JException("Unable [$index] ${this.javaClass.simpleName} is not and object")
    }
    fun getFloat(index: Int): Float {
        throw JException("Unable [$index] ${this.javaClass.simpleName} is not and object")
    }
    fun optString(index: Int): String? {
        throw JException("Unable [$index] ${this.javaClass.simpleName} is not and object")
    }
    fun getString(index: Int): String {
        throw JException("Unable [$index] ${this.javaClass.simpleName} is not and object")
    }
    fun optObject(index: Int): JObject? {
        throw JException("Unable [$index] ${this.javaClass.simpleName} is not and object")
    }
    fun getObject(index: Int): JObject {
        throw JException("Unable [$index] ${this.javaClass.simpleName} is not and object")
    }

    operator fun get(key: String): JValue {
        throw JException("Unable [$key] ${this.javaClass.simpleName} is not and object")
    }
    
    fun opt(key: String): JValue? {
        return null
    }
    
    operator fun get(index: Int): JValue {
        throw JException("Unable [$index] ${this.javaClass.simpleName} is not an array")
    }

    fun opt(index: Int): JValue? {
        return null
    }

    fun toInt(): Int {
        throw JException("Cannot convert ${this.javaClass.simpleName} to int")
    }
    fun toIntOrNull(): Int? {
        return null
    }
    fun toLong(): Long {
        throw JException("Cannot convert ${this.javaClass.simpleName} to long")
    }
    fun toLongOrNull(): Long? {
        return null
    }
    fun toDouble(): Double {
        throw JException("Cannot convert ${this.javaClass.simpleName} to double")
    }
    fun toDoubleOrNull(): Double? {
        return null
    }
    fun toFloat(): Float {
        throw JException("Cannot convert ${this.javaClass.simpleName} to float")
    }
    fun toFloatOrNull(): Float? {
        return null
    }
    fun asString(): String {
        throw JException("Cannot convert ${this.javaClass.simpleName} to String")
    }
    fun asStringOrNull(): String? {
        return null
    }
    fun toStrictBoolean(): Boolean {
        throw JException("Cannot convert ${this.javaClass.simpleName} to Boolean")
    }
    fun toStrictBooleanOrNull(): Boolean? {
        return null
    }
    fun toBoolean(): Boolean {
        throw JException("Cannot convert ${this.javaClass.simpleName} to Boolean")
    }
    fun toObjectOrNull(): JObject? {
        return this as? JObject
    }
    fun toObject(): JObject {
        return toObjectOrNull() ?: throw JException("Cannot convert ${this.javaClass.simpleName} to object")
    }
    fun toArrayOrNull(): JArray? {
        return this as? JArray
    }
    fun toArray(): JArray {
        return toArrayOrNull() ?: throw JException("Cannot convert ${this.javaClass.simpleName} to array")
    }

    fun tryExtract(keys: List<Any>): JValue? {
        var result: JValue = this

        for (key in keys) {
            val index = key as? Int
            result = if (index != null) {
                result.opt(index) ?: return null
            } else {
                result.opt(key.toString()) ?: return null
            }
        }

        return if (result === this) {
            null
        } else {
            result
        }
    }
    
    fun tryExtract(keyPath: String): JValue? {
        val keys = keyPath.split('.').map { 
            it.toIntOrNull() ?: it
        }
        
        return tryExtract(keys = keys)
    }

    fun extract(keys: List<Any>): JValue {
        return tryExtract(keys) ?: throw JException("Unable to extract value $keys")
    }

    fun extract(keyPath: String): JValue {
        return tryExtract(keyPath) ?: throw JException("Unable to extract value $keyPath")
    }
    
    fun tryExtractInt(keys: List<Any>): Int? {
        return tryExtract(keys)?.toIntOrNull()
    }
    
    fun tryExtractLong(keys: List<Any>): Long? {
        return tryExtract(keys)?.toLongOrNull()
    }
    
    fun tryExtractDouble(keys: List<Any>): Double? {
        return tryExtract(keys)?.toDoubleOrNull()
    }
    
    fun tryExtractFloat(keys: List<Any>): Float? {
        return tryExtract(keys)?.toFloatOrNull()
    }
    
    fun tryExtractString(keys: List<Any>): String? {
        return tryExtract(keys)?.asStringOrNull()
    }
    
    fun tryExtractInt(keyPath: String): Int? {
        return tryExtract(keyPath)?.toIntOrNull()
    }
    
    fun tryExtractLong(keyPath: String): Long? {
        return tryExtract(keyPath)?.toLongOrNull()
    }
    
    fun tryExtractDouble(keyPath: String): Double? {
        return tryExtract(keyPath)?.toDoubleOrNull()
    }
    
    fun tryExtractFloat(keyPath: String): Float? {
        return tryExtract(keyPath)?.toFloatOrNull()
    }
    
    fun extractString(keyPath: String): String {
        return tryExtract(keyPath)?.asStringOrNull() ?: throw JException("Unable to extract string $keyPath")
    }
    
    fun extractInt(keyPath: String): Int {
        return tryExtract(keyPath)?.toIntOrNull() ?: throw JException("Unable to extract int $keyPath")
    }
    
    fun extractLong(keyPath: String): Long {
        return tryExtract(keyPath)?.toLongOrNull() ?: throw JException("Unable to extract long $keyPath")
    }
    
    fun extractDouble(keyPath: String): Double {
        return tryExtract(keyPath)?.toDoubleOrNull() ?: throw JException("Unable to extract double $keyPath")
    }
    
    fun extractFloat(keyPath: String): Float {
        return tryExtract(keyPath)?.toFloatOrNull() ?: throw JException("Unable to extract float $keyPath")
    }

    fun extractString(keys: List<Any>): String {
        return tryExtract(keys)?.asStringOrNull() ?: throw JException("Unable to extract string $keys")
    }

    fun extractInt(keys: List<Any>): Int {
        return tryExtract(keys)?.toIntOrNull() ?: throw JException("Unable to extract int $keys")
    }

    fun extractLong(keys: List<Any>): Long {
        return tryExtract(keys)?.toLongOrNull() ?: throw JException("Unable to extract long $keys")
    }

    fun extractDouble(keys: List<Any>): Double {
        return tryExtract(keys)?.toDoubleOrNull() ?: throw JException("Unable to extract double $keys")
    }

    fun extractFloat(keys: List<Any>): Float {
        return tryExtract(keys)?.toFloatOrNull() ?: throw JException("Unable to extract float $keys")
    }
}