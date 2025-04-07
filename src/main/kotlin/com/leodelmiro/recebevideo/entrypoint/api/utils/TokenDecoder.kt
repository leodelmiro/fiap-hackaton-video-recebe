package com.leodelmiro.recebevideo.entrypoint.api.utils

import com.auth0.jwt.JWT


object TokenDecoder {
    fun decodeUsername(token: String): String {
        val decodedJWT = JWT.decode(token.split(" ")[1])
        val username = decodedJWT.getClaim("cognito:username").asString()
        return username
    }
}