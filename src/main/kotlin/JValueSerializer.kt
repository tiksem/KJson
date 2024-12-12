package com.kjson

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.*
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.*

@OptIn(ExperimentalSerializationApi::class)
private fun defer(deferred: () -> SerialDescriptor): SerialDescriptor = object : SerialDescriptor {

    private val original: SerialDescriptor by lazy(deferred)

    override val serialName: String
        get() = original.serialName
    override val kind: SerialKind
        get() = original.kind
    override val elementsCount: Int
        get() = original.elementsCount

    override fun getElementName(index: Int): String = original.getElementName(index)
    override fun getElementIndex(name: String): Int = original.getElementIndex(name)
    override fun getElementAnnotations(index: Int): List<Annotation> = original.getElementAnnotations(index)
    override fun getElementDescriptor(index: Int): SerialDescriptor = original.getElementDescriptor(index)
    override fun isElementOptional(index: Int): Boolean = original.isElementOptional(index)
}

internal object JValueSerializer : KSerializer<JValue> {
    @OptIn(ExperimentalSerializationApi::class, InternalSerializationApi::class)
    override val descriptor: SerialDescriptor =
        buildSerialDescriptor("com.kjson.JValue", PolymorphicKind.SEALED) {
            // Resolve cyclic dependency in descriptors by late binding
            element("JPrimitive", defer { JPrimitiveSerializer.descriptor })
            element("JObject", JObjectSerializer.descriptor )
            element("JArray", defer { JArraySerializer.descriptor })
        }

    override fun serialize(encoder: Encoder, value: JValue) {
        when (value) {
            is JPrimitive -> encoder.encodeSerializableValue(JPrimitiveSerializer, value)
            is JObject -> encoder.encodeSerializableValue(JObjectSerializer, value)
            is JArray -> encoder.encodeSerializableValue(JArraySerializer, value)
        }
    }

    override fun deserialize(decoder: Decoder): JValue {
        val input = decoder as JsonDecoder
        return input.decodeJsonElement().toJValue() ?: throw JException("Corrupted data, null received")
    }

}

internal object JObjectSerializer : KSerializer<JObject> {
    override val descriptor: SerialDescriptor = object : SerialDescriptor by MapSerializer(String.serializer(), JValueSerializer).descriptor {
        @ExperimentalSerializationApi
        override val serialName: String = "com.kjson.JObject"
    }

    override fun deserialize(decoder: Decoder): JObject {
        val input = decoder as JsonDecoder
        return JObject(map = input.decodeJsonElement() as? JsonObject ?: throw JException("Failed to decode JObject"))
    }

    override fun serialize(encoder: Encoder, value: JObject) {
        val output = encoder as JsonEncoder
        output.encodeJsonElement(value.map)
    }
}

internal object JArraySerializer : KSerializer<JArray> {
    override val descriptor: SerialDescriptor =  object : SerialDescriptor by ListSerializer(JValueSerializer).descriptor {
        @ExperimentalSerializationApi
        override val serialName: String = "com.kjson.JArray"
    }

    override fun deserialize(decoder: Decoder): JArray {
        val input = decoder as JsonDecoder
        return JArray(value = input.decodeJsonElement() as? JsonArray ?: throw JException("Failed to decode JArray"))
    }

    override fun serialize(encoder: Encoder, value: JArray) {
        val output = encoder as JsonEncoder
        output.encodeJsonElement(value.value)
    }
}

internal object JPrimitiveSerializer : KSerializer<JPrimitive> {
    override val descriptor: SerialDescriptor =  object : SerialDescriptor by PrimitiveSerialDescriptor("com.kjson.JPrimitive", PrimitiveKind.STRING) {}

    override fun deserialize(decoder: Decoder): JPrimitive {
        val input = decoder as JsonDecoder
        return JPrimitive(value = input.decodeJsonElement() as? JsonPrimitive)
    }

    override fun serialize(encoder: Encoder, value: JPrimitive) {
        val output = encoder as JsonEncoder
        if (value.value != null) {
            output.encodeJsonElement(value.value)
        }
    }
}