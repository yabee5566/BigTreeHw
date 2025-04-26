package com.iiaannppaann.bigtreehw.core.network.http.serialization

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

object SafeFloatAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): Float =
        when (reader.peek()) {
            JsonReader.Token.NULL -> {
                reader.nextNull<Unit>()
                0F
            }
            JsonReader.Token.STRING -> {
                val str = reader.nextString()
                str.toFloatOrNull() ?: 0F
            }
            JsonReader.Token.NUMBER -> {
                reader.nextDouble().toFloat()
            }
            else -> {
                throw JsonDataException("Expected LONG or STRING but was ${reader.peek()} at path ${reader.path}")
            }
        }

    @ToJson
    fun toJson(
        writer: JsonWriter,
        value: Float,
    ) {
        writer.value(value)
    }
}
