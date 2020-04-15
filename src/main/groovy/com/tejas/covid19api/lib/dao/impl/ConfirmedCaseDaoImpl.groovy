package com.tejas.covid19api.lib.dao.impl

import com.google.cloud.bigquery.TableResult
import com.tejas.covid19api.lib.constants.DatabaseConstants
import com.tejas.covid19api.lib.dao.ConfirmedCaseDao
import com.tejas.covid19api.lib.manager.BigQueryManager
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
class ConfirmedCaseDaoImpl implements ConfirmedCaseDao {
    @Autowired
    BigQueryManager queryManager

    @Override
    TableResult getTotalConfirmed() {
        StringBuilder sqlText = new StringBuilder("SELECT SUM(confirmed) AS total_confirmed FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE ${DatabaseConstants.LATEST_DATE_QUERY_STRING} ")
                .append("HAVING SUM(confirmed) IS NOT NULL;")

        return queryManager.runBigQuery(sqlText.toString())
    }

    @Override
    TableResult getConfirmedByCountry(String countryName) {
        StringBuilder sqlText = new StringBuilder("SELECT SUM(confirmed) AS total_confirmed_country FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE UPPER(country_region) = '${countryName}' AND ${DatabaseConstants.LATEST_DATE_QUERY_STRING} ")
                .append("HAVING SUM(confirmed) IS NOT NULL;")

        return queryManager.runBigQuery(sqlText.toString())
    }

    @Override
    TableResult getConfirmedTillDateByCountry(String countryName, String date) {
        StringBuilder sqlText = new StringBuilder("SELECT SUM(confirmed) AS confirmed_till_date FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE UPPER(country_region) = '${countryName}' AND date = '${date}' ")
                .append("HAVING SUM(confirmed) IS NOT NULL;")

        return queryManager.runBigQuery(sqlText.toString())
    }

    @Override
    TableResult getTotalConfirmedTillDate(String date) {
        StringBuilder sqlText = new StringBuilder("SELECT SUM(confirmed) AS confirmed_till_date FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE date = '${date}' ")
                .append("HAVING SUM(confirmed) IS NOT NULL;")

        return queryManager.runBigQuery(sqlText.toString())
    }

    @Override
    TableResult getConfirmedGrowthByCountry(String countryName) {
        StringBuilder sqlText = new StringBuilder("SELECT date, SUM(confirmed) AS country_confirmed_growth FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE UPPER(country_region) = '${countryName}' ")
                .append("GROUP BY date ")
                .append("HAVING SUM(confirmed) IS NOT NULL ")
                .append("ORDER BY date ASC;")

        return queryManager.runBigQuery(sqlText.toString())
    }
}
