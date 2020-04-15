package com.tejas.covid19api.lib.exception

class UnauthorizedException extends GroovyRuntimeException {
    UnauthorizedException(String message) {
        super(message)
    }
}