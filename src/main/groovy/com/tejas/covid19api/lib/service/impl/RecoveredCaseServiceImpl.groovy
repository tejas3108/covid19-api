package com.tejas.covid19api.lib.service.impl

import com.google.cloud.bigquery.FieldValueList
import com.google.cloud.bigquery.TableResult
import com.tejas.covid19api.domain.CaseSummary
import com.tejas.covid19api.lib.dao.RecoveredCaseDao
import com.tejas.covid19api.lib.exception.NotFoundException
import com.tejas.covid19api.lib.exception.ValidationException
import com.tejas.covid19api.lib.manager.CountryCodeCSVParser
import com.tejas.covid19api.lib.service.RecoveredCaseService
import com.tejas.covid19api.lib.util.DateUtil
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@CompileStatic
@Service
class RecoveredCaseServiceImpl implements RecoveredCaseService {
    @Autowired
    RecoveredCaseDao dao

    @Autowired
    CountryCodeCSVParser countryCodeParser
    
    @Override
    CaseSummary getTotalRecovered() {
        CaseSummary summary = null

        TableResult result = dao.getTotalRecovered()
        if(result.totalRows > 0) {
            for (FieldValueList row : result.iterateAll()) {
                summary = new CaseSummary(recovered: row.get("total_recovered")?.getLongValue())
            }

            return summary
        }
        throw new NotFoundException('exception.no.data.found')
    }

    @Override
    CaseSummary getRecoveredByCountry(String countryCode) {
        CaseSummary summaryByCountry = null

        String countryName = countryCodeParser.getCodeMap().get(countryCode)
        if(countryName) {
            TableResult result = dao.getRecoveredByCountry(countryName.toUpperCase())
            if(result.totalRows > 0) {
                for (FieldValueList row : result.iterateAll()) {
                    summaryByCountry = new CaseSummary(
                            recovered: row.get("total_recovered_country")?.getLongValue(),
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
    List<CaseSummary> getRecoveredGrowthByCountry(String countryCode) {
        List<CaseSummary> growthList = new ArrayList<>()

        String countryName = countryCodeParser.getCodeMap().get(countryCode)
        if(countryName) {
            TableResult result = dao.getRecoveredGrowthByCountry(countryName.toUpperCase())
            if(result.totalRows > 0) {
                for (FieldValueList row : result.iterateAll()) {
                    growthList << new CaseSummary(
                            recovered: row.get("country_recovered_growth").getLongValue(),
                            date: row.get("date")?.getStringValue()
                    )
                }

                return growthList
            }
            throw new NotFoundException('exception.no.data.found')
        }
        throw new NotFoundException('exception.invalid.country.code')
    }

    CaseSummary getTotalRecoveredTillDate(String date) {
        validateDate(date)
        CaseSummary recoveredTillDate = null

        TableResult result = dao.getTotalRecoveredTillDate(date)
        if(result.totalRows > 0) {
            for (FieldValueList row : result.iterateAll()) {
                recoveredTillDate = new CaseSummary(
                        recovered: row.get("recovered_till_date")?.getLongValue(),
                        date: date
                )
            }

            return recoveredTillDate
        }

        throw new NotFoundException('exception.no.data.found')
    }

    @Override
    CaseSummary getRecoveredTillDateByCountry(String countryCode, String date) {
        validateDate(date)
        CaseSummary recoveredTillDateByCountry = null

        String countryName = countryCodeParser.getCodeMap().get(countryCode)
        if(countryName) {
            TableResult result = dao.getRecoveredTillDateByCountry(countryName.toUpperCase(), date)
            if(result.totalRows > 0) {
                for (FieldValueList row : result.iterateAll()) {
                    recoveredTillDateByCountry = new CaseSummary(
                            recovered: row.get("recovered_till_date")?.getLongValue(),
                            date: date
                    )
                }

                return recoveredTillDateByCountry
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
