package com.tejas.covid19api.api.mapper.v1.deaths

import com.tejas.covid19api.api.dto.v1.CaseSummaryResponseDtoV1
import com.tejas.covid19api.api.mapper.v1.BaseMapper
import com.tejas.covid19api.domain.CaseSummary
import groovy.transform.CompileStatic
import org.springframework.stereotype.Component

@CompileStatic
@Component
class DeathCaseMapper extends BaseMapper{
    List<CaseSummaryResponseDtoV1> mapDomainListToDto(List <CaseSummary> caseSummaryList) {
        List<CaseSummaryResponseDtoV1> responseList = new ArrayList<>()
        caseSummaryList?.each {summary ->
            responseList << coreMapper.map(summary, CaseSummaryResponseDtoV1)
        }

        return responseList
    }
}
