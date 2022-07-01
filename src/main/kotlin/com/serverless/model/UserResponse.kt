package com.serverless.model

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.nio.charset.StandardCharsets
import java.util.*

class UserResponse(
        val statusCode: Int = 200,
        var body: String? = null
) {
    private constructor(builder: Builder) : this(
            builder.statusCode,
            builder.rawBody
    )

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var LOG: Logger = LogManager.getLogger(Builder::class.java)
        var objectMapper: ObjectMapper = ObjectMapper()

        var statusCode: Int = 200
        var rawBody: String? = null
        var objectBody: List<User>? = null
        var binaryBody: ByteArray? = null
        fun build(): UserResponse {
            var body: String? = null

            if (rawBody != null) {
                body = rawBody as String
            } else if (objectBody != null) {
                try {
                    body = objectMapper.writeValueAsString(objectBody)
                } catch (e: JsonProcessingException) {
                    LOG.error("failed to serialize object", e)
                    throw RuntimeException(e)
                }
            } else if (binaryBody != null) {
                body = String(Base64.getEncoder().encode(binaryBody), StandardCharsets.UTF_8)
            }
            return UserResponse(statusCode, body)
        }
    }
}