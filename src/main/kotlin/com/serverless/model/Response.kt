package com.serverless.model

class Response(message:String, input:Map<String, Any>) {
  val message: String = message
    get
  val input: Map<String, Any> = input
    get
}