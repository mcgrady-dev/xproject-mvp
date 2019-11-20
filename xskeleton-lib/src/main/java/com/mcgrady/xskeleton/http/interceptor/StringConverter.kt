package com.mcgrady.xskeleton.http.interceptor

import com.google.gson.*
import java.lang.reflect.Type

/**
 * Created by mcgrady on 2019-08-30.
 */
class StringConverter : JsonSerializer<String?>, JsonDeserializer<String> {
    override fun serialize(src: String?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return src?.let { JsonPrimitive(it) } ?: JsonPrimitive("")
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): String {
        return json.asJsonPrimitive.asString
    }
}