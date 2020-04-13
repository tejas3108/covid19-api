package com.tejas.covid19api.api.web.v1

import com.tejas.covid19api.api.dto.v1.CaseSummaryResponseDtoV1
import com.tejas.covid19api.api.mapper.v1.deaths.DeathCaseMapper
import com.tejas.covid19api.lib.service.DeathCaseService
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
@RequestMapping("/covid19/v1/deaths")
class DeathCaseController {
    @Autowired
    DeathCaseService baseService

    @Autowired
    DeathCaseMapper mapper

    @RequestMapping(value = "total", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getTotalDeaths(){
        return mapper.coreMapper.map(baseService.getTotalDeaths(), CaseSummaryResponseDtoV1)
    }

    @RequestMapping(value = "country/{countryName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getTotalDeathsByCountry(
            @PathVariable String countryName
    ){
        return mapper.coreMapper.map(baseService.getDeathsByCountry(countryName), CaseSummaryResponseDtoV1)
    }

    @RequestMapping(value = "country/{countryName}/growth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<CaseSummaryResponseDtoV1> getDeathGrowthByCountry(
            @PathVariable String countryName
    ){
        return mapper.mapDomainListToDto(baseService.getDeathGrowthByCountry(countryName))
    }
}