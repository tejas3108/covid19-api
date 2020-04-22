package com.tejas.covid19api.lib.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(code= HttpStatus.NOT_FOUND)
class NotFoundException extends GroovyRuntimeException{
    NotFoundException(String message) {
        super(message)
    }
}
