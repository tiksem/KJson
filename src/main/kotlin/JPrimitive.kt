package com.kjson

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*

class JPrimitive(private val value: JsonPrimitive) : JValue {
    override fun toInt(): Int {
        return toIntOrNull() ?: throw JException("Unable to convert ${value.content} to int")
    }

    override fun toIntOrNull(): Int? {
        return value.intOrNull
    }

    override fun toLong(): Long {
        return toLongOrNull() ?: throw JException("Unable to convert ${value.content} to long")
    }

    override fun toLongOrNull(): Long? {
        return value.longOrNull
    }

    override fun toDouble(): Double {
        return toDoubleOrNull() ?: throw JException("Unable to convert ${value.content} to double")
    }

    override fun toDoubleOrNull(): Double? {
        return value.doubleOrNull
    }

    override fun toFloat(): Float {
        return toFloatOrNull() ?: throw JException("Unable to convert ${value.content} to float")
    }

    override fun toFloatOrNull(): Float? {
        return value.floatOrNull
    }

    override fun asString(): String {
        return value.content
    }

    override fun asStringOrNull(): String {
        return asString()
    }

    override fun toBoolean(): Boolean {
        if (value.content == "true") {
            return true
        }

        if (value.content == "false") {
            return false
        }

        val asInt = toIntOrNull() ?: throw JException("Unable to convert ${value.content} to boolean")
        return asInt != 0
    }

    override fun toStrictBoolean(): Boolean {
        return toStrictBooleanOrNull() ?: throw JException("Unable to convert ${value.content} to boolean")
    }

    override fun toStrictBooleanOrNull(): Boolean? {
        return value.booleanOrNull
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is JPrimitive) {
            return false
        }

        return other.value == value
    }

    override fun toString(): String {
        return value.content
    }
}