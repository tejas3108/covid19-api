package com.tejas.covid19api.domain

import groovy.transform.CompileStatic

@CompileStatic
class GenericError {
    String className
    Object message

    GenericError(Exception e) {
        this.className = e.class.name
        this.message = e.message
    }

}
