package com.tejas.covid19api.api.web.v1

import com.tejas.covid19api.api.dto.v1.CaseSummaryResponseDtoV1
import com.tejas.covid19api.api.mapper.v1.casesummary.CaseSummaryMapper
import com.tejas.covid19api.lib.service.CaseSummaryService
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
@RequestMapping("/covid19/v1/summary")
class CaseSummaryController {
    @Autowired
    CaseSummaryService caseSummaryService
    @Autowired
    CaseSummaryMapper mapper

    @RequestMapping(value = "total", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getTotalSummary(){
        return mapper.coreMapper.map(caseSummaryService.getTotalSummary(), CaseSummaryResponseDtoV1)
    }

    @RequestMapping(value = "country/{countryName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getTotalSummaryByCountry(
            @PathVariable String countryName
    ){
        return mapper.coreMapper.map(caseSummaryService.getSummaryByCountry(countryName), CaseSummaryResponseDtoV1)
    }

    @RequestMapping(value = "date/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getTotalSummaryByDate(
            @PathVariable String date
    ){
        return mapper.coreMapper.map(caseSummaryService.getTotalSummaryTillDate(date), CaseSummaryResponseDtoV1)
    }


    @RequestMapping(value = "country/{countryName}/growth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<CaseSummaryResponseDtoV1> getSummaryGrowthByCountry(
            @PathVariable String countryName
    ){
        return mapper.mapDomainListToDto(caseSummaryService.getSummaryGrowthByCountry(countryName))
    }

    @RequestMapping(value = "country/{countryName}/date/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getSummaryTillDateByCountry(
            @PathVariable String countryName,
            @PathVariable String date
    ){
        return mapper.coreMapper.map(caseSummaryService.getSummaryTillDateByCountry(countryName, date), CaseSummaryResponseDtoV1)
    }
}
