package com.tejas.covid19api.api.web.v1

import com.tejas.covid19api.api.dto.v1.CaseSummaryResponseDtoV1
import com.tejas.covid19api.api.mapper.v1.casesummary.CaseSummaryMapper
import com.tejas.covid19api.lib.service.RecoveredCaseService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@Slf4j
@CompileStatic
@RestController
@RequestMapping("/covid19/v1/recovered")
class RecoveredCaseController {
    @Autowired
    RecoveredCaseService recoveredCaseService
    
    @Autowired
    CaseSummaryMapper mapper

    @RequestMapping(value = "total", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getTotalRecovered(){
        return mapper.coreMapper.map(recoveredCaseService.getTotalRecovered(), CaseSummaryResponseDtoV1)
    }

    @RequestMapping(value = "country/{countryName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getTotalRecoveredByCountry(
            @PathVariable String countryName
    ){
        return mapper.coreMapper.map(recoveredCaseService.getRecoveredByCountry(countryName), CaseSummaryResponseDtoV1)
    }

    @RequestMapping(value = "date/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getTotalRecoveredByDate(
            @PathVariable String date
    ){
        return mapper.coreMapper.map(recoveredCaseService.getTotalRecoveredTillDate(date), CaseSummaryResponseDtoV1)
    }


    @RequestMapping(value = "country/{countryName}/growth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<CaseSummaryResponseDtoV1> getRecoveredGrowthByCountry(
            @PathVariable String countryName
    ){
        return mapper.mapDomainListToDto(recoveredCaseService.getRecoveredGrowthByCountry(countryName))
    }

    @RequestMapping(value = "country/{countryName}/date/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getRecoveredTillDateByCountry(
            @PathVariable String countryName,
            @PathVariable String date
    ){
        return mapper.coreMapper.map(recoveredCaseService.getRecoveredTillDateByCountry(countryName, date), CaseSummaryResponseDtoV1)
    }
}
