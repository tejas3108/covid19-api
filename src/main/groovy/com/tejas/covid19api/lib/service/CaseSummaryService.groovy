package com.tejas.covid19api.lib.service

import com.tejas.covid19api.domain.CaseSummary
import groovy.transform.CompileStatic

@CompileStatic
interface CaseSummaryService {
    CaseSummary getTotalSummary()

    CaseSummary getSummaryByCountry(String countryCode)

    CaseSummary getTotalSummaryTillDate(String date)

    List<CaseSummary> getSummaryGrowthByCountry(String countryCode)

    CaseSummary getSummaryTillDateByCountry(String countryCode, String date)

}