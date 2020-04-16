package com.tejas.covid19api.lib.dao.impl

import com.google.cloud.bigquery.TableResult
import com.tejas.covid19api.lib.constants.DatabaseConstants
import com.tejas.covid19api.lib.dao.CaseSummaryDao
import com.tejas.covid19api.lib.manager.BigQueryManager
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
class CaseSummaryDaoImpl implements CaseSummaryDao {
    @Autowired
    BigQueryManager queryManager

    final String HAVING_STRING = "HAVING SUM(confirmed) IS NOT NULL AND SUM(deaths) IS NOT NULL AND SUM(recovered) IS NOT NULL "

    @Override
    TableResult getTotalSummary() {
        StringBuilder sqlText = new StringBuilder("SELECT SUM(confirmed) AS total_confirmed, SUM(deaths) AS total_deaths, SUM(recovered) as total_recovered FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE ${DatabaseConstants.LATEST_DATE_QUERY_STRING} ")
                .append(HAVING_STRING)

        return queryManager.runBigQuery(sqlText.toString())
    }

    @Override
    TableResult getSummaryByCountry(String countryName) {
        StringBuilder sqlText = new StringBuilder("SELECT SUM(confirmed) AS total_confirmed_country, SUM(deaths) AS total_deaths_country, SUM(recovered) as total_recovered_country " +
                "FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE UPPER(country_region) = '${countryName}' AND ${DatabaseConstants.LATEST_DATE_QUERY_STRING} ")
                .append(HAVING_STRING)

        return queryManager.runBigQuery(sqlText.toString())
    }

    @Override
    TableResult getSummaryTillDateByCountry(String countryName, String date) {
        StringBuilder sqlText = new StringBuilder("SELECT SUM(confirmed) AS confirmed_till_date, SUM(deaths) AS deaths_till_date, SUM(recovered) as recovered_till_date FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE UPPER(country_region) = '${countryName}' AND date = '${date}' ")
                .append(HAVING_STRING)

        return queryManager.runBigQuery(sqlText.toString())
    }

    @Override
    TableResult getTotalSummaryTillDate(String date) {
        StringBuilder sqlText = new StringBuilder("SELECT SUM(confirmed) AS confirmed_till_date, SUM(deaths) AS deaths_till_date, SUM(recovered) as recovered_till_date " +
                "FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE date = '${date}' ")
                .append(HAVING_STRING)

        return queryManager.runBigQuery(sqlText.toString())
    }

    @Override
    TableResult getSummaryGrowthByCountry(String countryName) {
        StringBuilder sqlText = new StringBuilder("SELECT date, SUM(confirmed) AS country_confirmed_growth, SUM(deaths) AS country_deaths_growth, SUM(recovered) as country_recovered_growth " +
                "FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE UPPER(country_region) = '${countryName}' ")
                .append("GROUP BY date ")
                .append(HAVING_STRING)
                .append("ORDER BY date ASC;")

        return queryManager.runBigQuery(sqlText.toString())
    }
}
