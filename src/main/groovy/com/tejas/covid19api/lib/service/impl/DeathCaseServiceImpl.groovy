package com.tejas.covid19api.lib.service.impl

import com.google.cloud.bigquery.FieldValueList
import com.google.cloud.bigquery.TableResult
import com.tejas.covid19api.domain.CaseSummary
import com.tejas.covid19api.lib.dao.DeathCaseDao
import com.tejas.covid19api.lib.service.DeathCaseService
import com.tejas.covid19api.lib.util.DateUtil
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@CompileStatic
@Service
class DeathCaseServiceImpl implements DeathCaseService {
    @Autowired
    DeathCaseDao dao

    @Override
    CaseSummary getTotalDeaths() {
        CaseSummary summary = null

        TableResult result = dao.getTotalDeaths()
        for (FieldValueList row : result.iterateAll()) {
            summary = new CaseSummary(deaths: row.get("total_deaths")?.getLongValue())
        }

        return summary
    }

    @Override
    CaseSummary getDeathsByCountry(String countryName) {
        CaseSummary summaryByCountry = null

        TableResult result = dao.getDeathsByCountry(countryName.toUpperCase())
        for (FieldValueList row : result.iterateAll()) {
            summaryByCountry = new CaseSummary(
                    deaths: row.get("total_deaths_country")?.getLongValue(),
                    countryRegion: countryName.toUpperCase()
            )
        }

        return summaryByCountry
    }

    @Override
    List<CaseSummary> getDeathGrowthByCountry(String countryName) {
        List<CaseSummary> growthList = new ArrayList<>()

        TableResult result = dao.getDeathGrowthByCountry(countryName.toUpperCase())
        for (FieldValueList row : result.iterateAll()) {
            growthList << new CaseSummary(
                    deaths: row.get("country_deaths_growth").getLongValue(),
                    date: row.get("date")?.getStringValue()
            )
        }

        return growthList
    }

    @Override
    CaseSummary getDeathsTillDate(String countryName, String date) {
        validateDate(date)
        CaseSummary deathsTillDate = null

        TableResult result = dao.getDeathsTillDate(countryName.toUpperCase(), date)
        for (FieldValueList row : result.iterateAll()) {
            deathsTillDate = new CaseSummary(
                    deaths: row.get("deaths_till_date")?.getLongValue(),
                    countryRegion: countryName.toUpperCase(),
                    date: date
            )
        }

        return deathsTillDate
    }

    private validateDate(String date) {
        DateUtil.convertToDateTime(date)
    }

}
