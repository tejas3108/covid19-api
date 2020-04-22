package com.tejas.covid19api.api.mapper.v1

import groovy.transform.CompileStatic
import org.modelmapper.ModelMapper
import org.modelmapper.convention.MatchingStrategies
import org.springframework.stereotype.Component

@CompileStatic
@Component
class BaseMapper {

    static ModelMapper getCoreMapper() {
        ModelMapper modelMapper = new ModelMapper()
        modelMapper.configuration.matchingStrategy = MatchingStrategies.STRICT
        modelMapper.validate()

        return modelMapper
    }

}
