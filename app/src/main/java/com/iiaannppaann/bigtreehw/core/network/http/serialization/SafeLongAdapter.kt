package com.iiaannppaann.bigtreehw.core.network.http.serialization

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson

object SafeLongAdapter {
    @FromJson
    fun fromJson(reader: JsonReader): Long =
        when (reader.peek()) {
            JsonReader.Token.NULL -> {
                reader.nextNull<Unit>()
                0L
            }
            JsonReader.Token.STRING -> {
                val str = reader.nextString()
                str.toLongOrNull() ?: 0L
            }
            JsonReader.Token.NUMBER -> {
                reader.nextLong()
            }
            else -> {
                throw JsonDataException("Expected LONG or STRING but was ${reader.peek()} at path ${reader.path}")
            }
        }

    @ToJson
    fun toJson(
        writer: JsonWriter,
        value: Long,
    ) {
        writer.value(value)
    }
}
