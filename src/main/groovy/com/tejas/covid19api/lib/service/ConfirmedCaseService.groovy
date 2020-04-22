package com.tejas.covid19api.lib.service

import com.tejas.covid19api.domain.CaseSummary
import groovy.transform.CompileStatic

@CompileStatic
interface ConfirmedCaseService {
    CaseSummary getTotalConfirmed()

    CaseSummary getConfirmedByCountry(String countryCode)

    CaseSummary getTotalConfirmedTillDate(String date)

    List<CaseSummary> getConfirmedGrowthByCountry(String countryCode)

    CaseSummary getConfirmedTillDateByCountry(String countryCode, String date)
}