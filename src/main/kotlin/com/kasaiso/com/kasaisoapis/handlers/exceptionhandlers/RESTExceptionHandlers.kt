package com.kasaiso.com.kasaisoapis.handlers.exceptionhandlers

import com.kasaiso.com.kasaisoapis.exceptions.DatetimeParseException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class RESTExceptionHandlers: ResponseEntityExceptionHandler() {

    @ExceptionHandler(DatetimeParseException::class)
    fun handleDatetimeParseException(
        ex: Exception,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any>{
        return handleExceptionInternal(
            ex,
            null,
            headers,
            status,
            request
        )
    }


    override fun handleExceptionInternal(
        ex: Exception,
        body: Any?,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        logger.error("Error: " + ex.message)
        return super.handleExceptionInternal(ex, body, headers, status, request)
    }
}