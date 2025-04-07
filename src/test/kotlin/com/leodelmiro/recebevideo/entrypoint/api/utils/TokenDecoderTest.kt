package com.leodelmiro.recebevideo.entrypoint.api.utils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.leodelmiro.recebevideo.entrypoint.api.utils.TokenDecoder
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TokenDecoderTest {

    @Test
    fun `should decode username from token`() {
        val username = "testuser"
        val algorithm = Algorithm.HMAC256("secret")
        val token = "Bearer " + JWT.create()
            .withClaim("cognito:username", username)
            .sign(algorithm)

        val decodedUsername = TokenDecoder.decodeUsername(token)

        assertEquals(username, decodedUsername)
    }
}