package com.tejas.covid19api.api.web.v1

import com.tejas.covid19api.api.dto.v1.CaseSummaryResponseDtoV1
import com.tejas.covid19api.api.mapper.v1.casesummary.CaseSummaryMapper
import com.tejas.covid19api.lib.service.CaseSummaryService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.annotations.ApiResponse
import io.swagger.annotations.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@Api(tags = "V1 COVID-19 Overall Summary Data")
@Slf4j
@CompileStatic
@RestController
@RequestMapping("/covid19/v1/summary")
class CaseSummaryController {
    @Autowired
    CaseSummaryService caseSummaryService
    @Autowired
    CaseSummaryMapper mapper

    @ApiOperation(value = "Get latest summary of total confirmed, deaths, and recovered cases.")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Member Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Exception")
    ]
    )
    @RequestMapping(value = "total", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getTotalSummary() {
        return mapper.coreMapper.map(caseSummaryService.getTotalSummary(), CaseSummaryResponseDtoV1)
    }

    @ApiOperation(value = "Get latest summary of total confirmed, deaths, and recovered cases by country.")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Member Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Exception")
    ]
    )
    @RequestMapping(value = "country/{countryName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getTotalSummaryByCountry(
            @ApiParam(name = "countryName", value = "Country Name", required = true) @PathVariable(required = true) String countryName
    ) {
        return mapper.coreMapper.map(caseSummaryService.getSummaryByCountry(countryName), CaseSummaryResponseDtoV1)
    }

    @ApiOperation(value = "Get summary of total confirmed, deaths, and recovered cases by date. Accepted format is YYYY-MM-DD")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Member Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Exception")
    ]
    )
    @RequestMapping(value = "date/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getTotalSummaryByDate(
            @ApiParam(name = "date", value = "Date", required = true) @PathVariable(required = true) String date
    ) {
        return mapper.coreMapper.map(caseSummaryService.getTotalSummaryTillDate(date), CaseSummaryResponseDtoV1)
    }

    @ApiOperation(value = "Get a historical view of total confirmed, deaths, and recovered cases by country.")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Member Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Exception")
    ]
    )
    @RequestMapping(value = "country/{countryName}/growth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<CaseSummaryResponseDtoV1> getSummaryGrowthByCountry(
            @ApiParam(name = "countryName", value = "Country Name", required = true) @PathVariable(required = true) String countryName
    ) {
        return mapper.mapDomainListToDto(caseSummaryService.getSummaryGrowthByCountry(countryName))
    }

    @ApiOperation(value = "Get a summary of total confirmed, deaths, and recovered cases of a country on a particular date. Accepted format is YYYY-MM-DD")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Member Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Exception")
    ]
    )
    @RequestMapping(value = "country/{countryName}/date/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getSummaryTillDateByCountry(
            @ApiParam(name = "countryName", value = "Country Name", required = true) @PathVariable(required = true) String countryName,
            @ApiParam(name = "date", value = "Date", required = true) @PathVariable(required = true) String date
    ) {
        return mapper.coreMapper.map(caseSummaryService.getSummaryTillDateByCountry(countryName, date), CaseSummaryResponseDtoV1)
    }
}
