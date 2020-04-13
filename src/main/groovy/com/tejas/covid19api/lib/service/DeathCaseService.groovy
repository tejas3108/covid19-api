package com.tejas.covid19api.lib.service

import com.tejas.covid19api.domain.CaseSummary

interface DeathCaseService {
    CaseSummary getTotalDeaths()

    CaseSummary getDeathsByCountry(String countryName)

    List<CaseSummary> getDeathGrowthByCountry(String countryName)
}