package com.tejas.covid19api.lib.service.impl

import com.google.cloud.bigquery.FieldValueList
import com.google.cloud.bigquery.TableResult
import com.tejas.covid19api.domain.CaseSummary
import com.tejas.covid19api.lib.dao.CaseSummaryDao
import com.tejas.covid19api.lib.exception.NotFoundException
import com.tejas.covid19api.lib.exception.ValidationException
import com.tejas.covid19api.lib.service.CaseSummaryService
import com.tejas.covid19api.lib.util.DateUtil
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@CompileStatic
@Service
class CaseSummaryServiceImpl implements CaseSummaryService {
    @Autowired
    CaseSummaryDao dao

    @Override
    CaseSummary getTotalSummary() {
        CaseSummary summary = null

        TableResult result = dao.getTotalSummary()
        if(result.totalRows > 0) {
            for (FieldValueList row : result.iterateAll()) {
                summary = new CaseSummary(
                        confirmed: row.get("total_confirmed")?.getLongValue(),
                        deaths: row.get("total_deaths")?.getLongValue(),
                        recovered: row.get("total_recovered")?.getLongValue()
                )
            }

            return summary
        }

        throw new NotFoundException('exception.no.data.found')
    }

    @Override
    CaseSummary getSummaryByCountry(String countryName) {
        CaseSummary summaryByCountry = null

        TableResult result = dao.getSummaryByCountry(countryName.toUpperCase())
        if(result.totalRows > 0) {
            for (FieldValueList row : result.iterateAll()) {
                summaryByCountry = new CaseSummary(
                        confirmed: row.get("total_confirmed_country")?.getLongValue(),
                        deaths: row.get("total_deaths_country")?.getLongValue(),
                        recovered: row.get("total_recovered_country")?.getLongValue(),
                        countryRegion: countryName.toUpperCase()
                )
            }

            return summaryByCountry
        }

        throw new NotFoundException('exception.no.data.found')
    }

    @Override
    List<CaseSummary> getSummaryGrowthByCountry(String countryName) {
        List<CaseSummary> growthList = new ArrayList<>()

        TableResult result = dao.getSummaryGrowthByCountry(countryName.toUpperCase())
        if(result.totalRows > 0) {
            for (FieldValueList row : result.iterateAll()) {
                growthList << new CaseSummary(
                        confirmed: row.get("country_confirmed_growth").getLongValue(),
                        deaths: row.get("country_deaths_growth").getLongValue(),
                        recovered: row.get("country_recovered_growth").getLongValue(),
                        date: row.get("date")?.getStringValue()
                )
            }

            return growthList
        }

        throw new NotFoundException('exception.no.data.found')
    }

    CaseSummary getTotalSummaryTillDate(String date) {
        validateDate(date)
        CaseSummary confirmedTillDate = null

        TableResult result = dao.getTotalSummaryTillDate(date)
        if(result.totalRows > 0) {
            for (FieldValueList row : result.iterateAll()) {
                confirmedTillDate = new CaseSummary(
                        confirmed: row.get("confirmed_till_date")?.getLongValue(),
                        deaths: row.get("deaths_till_date")?.getLongValue(),
                        recovered: row.get("recovered_till_date")?.getLongValue(),
                        date: date
                )
            }

            return confirmedTillDate
        }

        throw new NotFoundException('exception.no.data.found')
    }

    @Override
    CaseSummary getSummaryTillDateByCountry(String countryName, String date) {
        validateDate(date)
        CaseSummary confirmedTillDateByCountry = null

        TableResult result = dao.getSummaryTillDateByCountry(countryName.toUpperCase(), date)
        if(result.totalRows > 0) {
            for (FieldValueList row : result.iterateAll()) {
                confirmedTillDateByCountry = new CaseSummary(
                        confirmed: row.get("confirmed_till_date")?.getLongValue(),
                        deaths: row.get("deaths_till_date")?.getLongValue(),
                        recovered: row.get("recovered_till_date")?.getLongValue(),
                        date: date
                )
            }

            return confirmedTillDateByCountry
        }

        throw new NotFoundException('exception.no.data.found')
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
