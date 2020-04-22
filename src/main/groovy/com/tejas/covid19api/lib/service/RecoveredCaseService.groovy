package com.tejas.covid19api.lib.service

import com.tejas.covid19api.domain.CaseSummary
import groovy.transform.CompileStatic

@CompileStatic
interface RecoveredCaseService {
    CaseSummary getTotalRecovered()

    CaseSummary getRecoveredByCountry(String countryCode)

    CaseSummary getTotalRecoveredTillDate(String date)

    List<CaseSummary> getRecoveredGrowthByCountry(String countryCode)

    CaseSummary getRecoveredTillDateByCountry(String countryCode, String date)
}