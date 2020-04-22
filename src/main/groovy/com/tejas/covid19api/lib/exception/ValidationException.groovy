package com.tejas.covid19api.lib.exception

class ValidationException extends GroovyRuntimeException {
    ValidationException(String message) {
        super(message)
    }
}