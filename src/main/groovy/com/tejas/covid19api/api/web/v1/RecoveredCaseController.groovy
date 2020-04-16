package com.tejas.covid19api.api.web.v1

import com.tejas.covid19api.api.dto.v1.CaseSummaryResponseDtoV1
import com.tejas.covid19api.api.mapper.v1.casesummary.CaseSummaryMapper
import com.tejas.covid19api.lib.service.RecoveredCaseService
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

@Api(tags = "V1 COVID-19 Recovered Cases Data")
@Slf4j
@CompileStatic
@RestController
@RequestMapping("/covid19/v1/recovered")
class RecoveredCaseController {
    @Autowired
    RecoveredCaseService recoveredCaseService
    
    @Autowired
    CaseSummaryMapper mapper

    @ApiOperation(value = "Get latest count of total recovered cases.")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Member Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Exception")
    ]
    )
    @RequestMapping(value = "total", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getTotalRecovered(){
        return mapper.coreMapper.map(recoveredCaseService.getTotalRecovered(), CaseSummaryResponseDtoV1)
    }

    @ApiOperation(value = "Get latest count of total recovered cases by country.")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Member Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Exception")
    ]
    )
    @RequestMapping(value = "country/{countryName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getTotalRecoveredByCountry(
            @ApiParam(name = "countryName", value = "Country Name", required = true) @PathVariable(required = true) String countryName
    ){
        return mapper.coreMapper.map(recoveredCaseService.getRecoveredByCountry(countryName), CaseSummaryResponseDtoV1)
    }

    @ApiOperation(value = "Get count of total recovered cases by date. Accepted format is YYYY-MM-DD")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Member Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Exception")
    ]
    )
    @RequestMapping(value = "date/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getTotalRecoveredByDate(
            @ApiParam(name = "date", value = "Date", required = true) @PathVariable(required = true) String date
    ){
        return mapper.coreMapper.map(recoveredCaseService.getTotalRecoveredTillDate(date), CaseSummaryResponseDtoV1)
    }

    @ApiOperation(value = "Get a historical view of total recovered cases by country.")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Member Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Exception")
    ]
    )
    @RequestMapping(value = "country/{countryName}/growth", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    List<CaseSummaryResponseDtoV1> getRecoveredGrowthByCountry(
            @ApiParam(name = "countryName", value = "Country Name", required = true) @PathVariable(required = true) String countryName
    ){
        return mapper.mapDomainListToDto(recoveredCaseService.getRecoveredGrowthByCountry(countryName))
    }

    @ApiOperation(value = "Get a count of total recovered cases of a country on a particular date. Accepted format is YYYY-MM-DD")
    @ApiResponses(value = [
            @ApiResponse(code = 200, message = "Successful"),
            @ApiResponse(code = 404, message = "Member Not Found"),
            @ApiResponse(code = 422, message = "Unprocessable Entity"),
            @ApiResponse(code = 500, message = "Internal Exception")
    ]
    )
    @RequestMapping(value = "country/{countryName}/date/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getRecoveredTillDateByCountry(
            @ApiParam(name = "countryName", value = "Country Name", required = true) @PathVariable(required = true) String countryName,
            @ApiParam(name = "date", value = "Date", required = true) @PathVariable(required = true) String date
    ){
        return mapper.coreMapper.map(recoveredCaseService.getRecoveredTillDateByCountry(countryName, date), CaseSummaryResponseDtoV1)
    }
}
