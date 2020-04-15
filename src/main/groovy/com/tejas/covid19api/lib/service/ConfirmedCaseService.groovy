package com.tejas.covid19api.lib.service

import com.tejas.covid19api.domain.CaseSummary
import groovy.transform.CompileStatic

@CompileStatic
interface ConfirmedCaseService {
    CaseSummary getTotalConfirmed()

    CaseSummary getConfirmedByCountry(String countryName)

    CaseSummary getTotalConfirmedTillDate(String date)

    List<CaseSummary> getConfirmedGrowthByCountry(String countryName)

    CaseSummary getConfirmedTillDateByCountry(String countryName, String date)
}