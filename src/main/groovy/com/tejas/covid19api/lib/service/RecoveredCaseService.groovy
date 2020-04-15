package com.tejas.covid19api.lib.service

import com.tejas.covid19api.domain.CaseSummary
import groovy.transform.CompileStatic

@CompileStatic
interface RecoveredCaseService {
    CaseSummary getTotalRecovered()

    CaseSummary getRecoveredByCountry(String countryName)

    CaseSummary getTotalRecoveredTillDate(String date)

    List<CaseSummary> getRecoveredGrowthByCountry(String countryName)

    CaseSummary getRecoveredTillDateByCountry(String countryName, String date)
}