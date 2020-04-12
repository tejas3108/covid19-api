package com.tejas.covid19api.api.dto.v1

import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.CompileStatic

@CompileStatic
@JsonInclude(JsonInclude.Include.NON_NULL)
class CaseSummaryResponseDtoV1 {
    String provinceState
    String countryRegion
    Date date
    Float latitude
    Float longitude
    Long confirmed
    Long deaths
    Long recovered
    Long active
}
