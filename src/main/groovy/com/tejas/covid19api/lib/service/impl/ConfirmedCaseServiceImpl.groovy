package com.tejas.covid19api.lib.service.impl

import com.google.cloud.bigquery.FieldValueList
import com.google.cloud.bigquery.TableResult
import com.tejas.covid19api.domain.CaseSummary
import com.tejas.covid19api.lib.dao.ConfirmedCaseDao
import com.tejas.covid19api.lib.exception.NotFoundException
import com.tejas.covid19api.lib.exception.ValidationException
import com.tejas.covid19api.lib.manager.CountryCodeCSVParser
import com.tejas.covid19api.lib.service.ConfirmedCaseService
import com.tejas.covid19api.lib.util.DateUtil
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@CompileStatic
@Service
class ConfirmedCaseServiceImpl implements ConfirmedCaseService{
    @Autowired
    ConfirmedCaseDao dao

    @Autowired
    CountryCodeCSVParser countryCodeParser

    @Override
    CaseSummary getTotalConfirmed() {
        CaseSummary summary = null

        TableResult result = dao.getTotalConfirmed()
        if(result.totalRows > 0) {
            for (FieldValueList row : result.iterateAll()) {
                summary = new CaseSummary(confirmed: row.get("total_confirmed")?.getLongValue())
            }

            return summary
        }
        throw new NotFoundException('exception.no.data.found')
    }

    @Override
    CaseSummary getConfirmedByCountry(String countryCode) {
        CaseSummary summaryByCountry = null

        String countryName = countryCodeParser.getCodeMap().get(countryCode)
        if(countryName) {
            TableResult result = dao.getConfirmedByCountry(countryName.toUpperCase())
            if(result.totalRows > 0) {
                for (FieldValueList row : result.iterateAll()) {
                    summaryByCountry = new CaseSummary(
                            confirmed: row.get("total_confirmed_country")?.getLongValue(),
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
    List<CaseSummary> getConfirmedGrowthByCountry(String countryCode) {
        List<CaseSummary> growthList = new ArrayList<>()

        String countryName = countryCodeParser.getCodeMap().get(countryCode)
        if(countryName) {
            TableResult result = dao.getConfirmedGrowthByCountry(countryName.toUpperCase())
            if(result.totalRows > 0) {
                for (FieldValueList row : result.iterateAll()) {
                    growthList << new CaseSummary(
                            confirmed: row.get("country_confirmed_growth").getLongValue(),
                            date: row.get("date")?.getStringValue()
                    )
                }

                return growthList
            }
            throw new NotFoundException('exception.no.data.found')
        }
        throw new NotFoundException('exception.invalid.country.code')
    }

    CaseSummary getTotalConfirmedTillDate(String date) {
        validateDate(date)
        CaseSummary confirmedTillDate = null

        TableResult result = dao.getTotalConfirmedTillDate(date)
        if(result.totalRows > 0) {
            for (FieldValueList row : result.iterateAll()) {
                confirmedTillDate = new CaseSummary(
                        confirmed: row.get("confirmed_till_date")?.getLongValue(),
                        date: date
                )
            }

            return confirmedTillDate
        }
        throw new NotFoundException('exception.no.data.found')
    }

    @Override
    CaseSummary getConfirmedTillDateByCountry(String countryCode, String date) {
        validateDate(date)
        CaseSummary confirmedTillDateByCountry = null

        String countryName = countryCodeParser.getCodeMap().get(countryCode)
        if(countryName) {
            TableResult result = dao.getConfirmedTillDateByCountry(countryName.toUpperCase(), date)
            if(result.totalRows > 0) {
                for (FieldValueList row : result.iterateAll()) {
                    confirmedTillDateByCountry = new CaseSummary(
                            confirmed: row.get("confirmed_till_date")?.getLongValue(),
                            date: date
                    )
                }

                return confirmedTillDateByCountry
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
