package com.serverless

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.serverless.model.ApiGatewayResponse
import com.serverless.model.Response
import com.serverless.model.User
import com.serverless.model.UserResponse
import org.apache.logging.log4j.LogManager
import java.util.*

class UserHandler:RequestHandler<User, UserResponse> {

    override fun handleRequest(input:User, context:Context): UserResponse {
        LOG.info("received: " + input.toString())
        input.uuid ?: run {
            input.uuid = UUID.randomUUID().toString();
        }
        val responseBody = listOf(input)
        return UserResponse.build {
            statusCode = 200
            objectBody = responseBody
        }
    }
    companion object {
        private val LOG = LogManager.getLogger(UserHandler::class.java)
    }
}