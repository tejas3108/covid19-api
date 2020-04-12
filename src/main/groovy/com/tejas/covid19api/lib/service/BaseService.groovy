package com.tejas.covid19api.lib.service

import com.tejas.covid19api.domain.CaseSummary

interface BaseService {
    CaseSummary getTotalDeaths()
}