package com.tejas.covid19api.lib.service.impl

import com.google.cloud.bigquery.FieldValueList
import com.google.cloud.bigquery.TableResult
import com.tejas.covid19api.domain.CaseSummary
import com.tejas.covid19api.lib.dao.DeathCaseDao
import com.tejas.covid19api.lib.exception.NotFoundException
import com.tejas.covid19api.lib.exception.ValidationException
import com.tejas.covid19api.lib.manager.CountryCodeCSVParser
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

    @Autowired
    CountryCodeCSVParser countryCodeParser

    @Override
    CaseSummary getTotalDeaths() {
        CaseSummary summary = null

        TableResult result = dao.getTotalDeaths()
        if(result.totalRows > 0) {
            for (FieldValueList row : result.iterateAll()) {
                summary = new CaseSummary(deaths: row.get("total_deaths")?.getLongValue())
            }

            return summary
        }
        throw new NotFoundException('exception.no.data.found')
    }

    @Override
    CaseSummary getDeathsByCountry(String countryCode) {
        CaseSummary summaryByCountry = null

        String countryName = countryCodeParser.getCodeMap().get(countryCode)
        if(countryName) {
            TableResult result = dao.getDeathsByCountry(countryName.toUpperCase())
            if(result.totalRows > 0) {
                for (FieldValueList row : result.iterateAll()) {
                    summaryByCountry = new CaseSummary(
                            deaths: row.get("total_deaths_country")?.getLongValue(),
                            countryRegion: countryName.toUpperCase()
                    )
                }

                return summaryByCountry
            }
            throw new NotFoundException('exception.no.data.found')
        }
        throw new NotFoundException('exception.invalid.country.code')
    }

    @Override
    List<CaseSummary> getDeathGrowthByCountry(String countryCode) {
        List<CaseSummary> growthList = new ArrayList<>()

        String countryName = countryCodeParser.getCodeMap().get(countryCode)
        if(countryName) {
            TableResult result = dao.getDeathGrowthByCountry(countryName.toUpperCase())
            if(result.totalRows > 0) {
                for (FieldValueList row : result.iterateAll()) {
                    growthList << new CaseSummary(
                            deaths: row.get("country_deaths_growth").getLongValue(),
                            date: row.get("date")?.getStringValue()
                    )
                }

                return growthList
            }
            throw new NotFoundException('exception.no.data.found')
        }
        throw new NotFoundException('exception.invalid.country.code')
    }

    CaseSummary getTotalDeathsTillDate(String date) {
        validateDate(date)
        CaseSummary deathsTillDate = null

        TableResult result = dao.getTotalDeathsTillDate(date)
        if(result.totalRows > 0) {
            for (FieldValueList row : result.iterateAll()) {
                deathsTillDate = new CaseSummary(
                        deaths: row.get("deaths_till_date")?.getLongValue(),
                        date: date
                )
            }

            return deathsTillDate
        }

        throw new NotFoundException('exception.no.data.found')
    }

    @Override
    CaseSummary getDeathsTillDateByCountry(String countryCode, String date) {
        validateDate(date)
        CaseSummary deathsTillDateByCountry = null

        String countryName = countryCodeParser.getCodeMap().get(countryCode)
        if(countryName) {
            TableResult result = dao.getDeathsTillDateByCountry(countryName.toUpperCase(), date)
            if(result.totalRows > 0) {
                for (FieldValueList row : result.iterateAll()) {
                    deathsTillDateByCountry = new CaseSummary(
                            deaths: row.get("deaths_till_date")?.getLongValue(),
                            date: date
                    )
                }

                return deathsTillDateByCountry
            }
            throw new NotFoundException('exception.no.data.found')
        }
        throw new NotFoundException('exception.invalid.country.code')
    }

    private validateDate(String date) {
        try {
            DateUtil.convertToDateTime(date)
        }
        catch (Exception e) {
            throw new ValidationException('exception.invalid.date')
        }
    }

}
