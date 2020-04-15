package com.tejas.covid19api.api.advice

import com.tejas.covid19api.domain.GenericError
import com.tejas.covid19api.lib.exception.*
import groovy.transform.CompileStatic
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

import javax.servlet.http.HttpServletRequest

@ControllerAdvice
@CompileStatic
class AppAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    GenericError handleNotFoundException(Exception exception, HttpServletRequest request) {
        return new GenericError(exception)
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseBody
    GenericError handleUnauthorizedException(Exception exception, HttpServletRequest request) {
        return new GenericError(exception)
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    GenericError handleUnprocessableEntityException(Exception exception, HttpServletRequest request) {
        return new GenericError(exception)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler([Exception.class, RuntimeException.class, GroovyRuntimeException.class, AuthenticationException.class])
    @ResponseBody
    GenericError handleGenericException(Exception exception, HttpServletRequest request) {
        return new GenericError(exception)
    }
}
