package com.tejas.covid19api.lib.exception

class AuthenticationException extends GroovyRuntimeException {
    AuthenticationException(String message) {
        super(message)
    }
}