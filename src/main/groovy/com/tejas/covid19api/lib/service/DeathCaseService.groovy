package com.tejas.covid19api.lib.service

import com.tejas.covid19api.domain.CaseSummary

interface DeathCaseService {
    CaseSummary getTotalDeaths()

    CaseSummary getDeathsByCountry(String countryCode)

    CaseSummary getTotalDeathsTillDate(String date)

    List<CaseSummary> getDeathGrowthByCountry(String countryCode)

    CaseSummary getDeathsTillDateByCountry(String countryCode, String date)
}