package com.leodelmiro.recebevideo.entrypoint.api.shared

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class GlobalControllerAdviceTest {
    private var globalControllerAdvice: GlobalControllerAdvice? = GlobalControllerAdvice()

    @Test
    fun `deve lidar com IllegalArgumentException retornando BadRequest`() {
        val ex: Exception = IllegalArgumentException("Invalid argument")
        val response = globalControllerAdvice!!.handleIllegalArgumentException(ex)

        Assertions.assertNotNull(response)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertEquals("Illegal Argument Exception", response.body!!.error)
        Assertions.assertEquals("Invalid argument", response.body!!.message)
    }

    @Test
    fun `deve lidar com IllegalStateException retornando BadRequest`() {
        val ex = IllegalStateException("Invalid state")
        val response = globalControllerAdvice!!.handleIllegalStateException(ex)

        Assertions.assertNotNull(response)
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        Assertions.assertEquals("Illegal State Exception", response.body!!.error)
        Assertions.assertEquals("Invalid state", response.body!!.message)
    }

    @Test
    fun `deve lidar GenericException retornando InternalServerError`() {
        val ex = Exception("Unexpected error")
        val response = globalControllerAdvice!!.handleGenericException(ex)

        Assertions.assertNotNull(response)
        Assertions.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        Assertions.assertEquals("Internal server error", response.body!!.error)
        Assertions.assertEquals("Unexpected error", response.body!!.message)
    }
}