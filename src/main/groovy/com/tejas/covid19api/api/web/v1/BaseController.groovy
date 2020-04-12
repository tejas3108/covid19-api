package com.tejas.covid19api.api.web.v1

import com.tejas.covid19api.api.dto.v1.CaseSummaryResponseDtoV1
import com.tejas.covid19api.api.mapper.v1.BaseMapper
import com.tejas.covid19api.lib.service.BaseService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@Slf4j
@CompileStatic
@RestController
@RequestMapping("/covid19/v1")
class BaseController {
    @Autowired
    BaseService baseService

    @Autowired
    BaseMapper mapper

    @RequestMapping(value = "deaths", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    CaseSummaryResponseDtoV1 getTotalDeaths(){
        return mapper.coreMapper.map(baseService.getTotalDeaths(), CaseSummaryResponseDtoV1)
    }
}
