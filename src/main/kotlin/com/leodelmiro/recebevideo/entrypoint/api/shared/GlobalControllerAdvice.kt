package com.leodelmiro.recebevideo.entrypoint.api.shared

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalControllerAdvice {
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: Exception): ResponseEntity<ErrorResponse> {
        log.error(ex.message)
        val errorResponse = ErrorResponse("Illegal Argument Exception", ex.message)
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(IllegalStateException::class)
    fun handleIllegalStateException(ex: IllegalStateException): ResponseEntity<ErrorResponse> {
        log.error(ex.message)
        val errorResponse = ErrorResponse("Illegal State Exception", ex.message)
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
        log.error(ex.message)
        val errorResponse = ErrorResponse("Internal server error", ex.message)
        return ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @JvmRecord
    data class ErrorResponse(val error: String, val message: String?)
    companion object {
        private val log: Logger = LoggerFactory.getLogger(GlobalControllerAdvice::class.java)
    }
}