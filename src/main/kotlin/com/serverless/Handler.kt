package com.serverless

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import org.apache.logging.log4j.LogManager
import java.util.*

class Handler:RequestHandler<Map<String, Any>, ApiGatewayResponse> {

    var envVar: String = System.getenv("APP_ENV_VARIABLE") ?: "Serverless"

    override fun handleRequest(input:Map<String, Any>, context:Context):ApiGatewayResponse {
        LOG.info("received: " + input.keys.toString())
        val responseBody = Response("Go $envVar! Your Kotlin function executed successfully!", input)
        return ApiGatewayResponse.build {
            statusCode = 200
            objectBody = responseBody
            headers = Collections.singletonMap<String, String>("X-Powered-By", "AWS Lambda & serverless")
        }
    }
    companion object {
        private val LOG = LogManager.getLogger(Handler::class.java)
    }
}