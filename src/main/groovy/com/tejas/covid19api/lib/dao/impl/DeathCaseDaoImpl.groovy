package com.tejas.covid19api.lib.dao.impl

import com.google.cloud.bigquery.TableResult
import com.tejas.covid19api.lib.dao.DeathCaseDao
import com.tejas.covid19api.lib.constants.DatabaseConstants
import com.tejas.covid19api.lib.manager.BigQueryManager
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
class DeathCaseDaoImpl implements DeathCaseDao{
    @Autowired
    BigQueryManager queryManager

    @Override
    TableResult getTotalDeaths() {
        StringBuilder sqlText = new StringBuilder("SELECT SUM(deaths) AS total_deaths FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
            .append("WHERE ${DatabaseConstants.latestDateQueryString};")

        return queryManager.runBigQuery(sqlText.toString())
    }

    @Override
    TableResult getDeathsByCountry(String countryName) {
        StringBuilder sqlText = new StringBuilder("SELECT SUM(deaths) AS total_deaths_country FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE UPPER(country_region) = '${countryName}' AND ${DatabaseConstants.latestDateQueryString};")

        return queryManager.runBigQuery(sqlText.toString())
    }

    @Override
    TableResult getDeathsTillDate(String countryName, String date) {
        StringBuilder sqlText = new StringBuilder("SELECT SUM(deaths) AS deaths_till_date FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE UPPER(country_region) = '${countryName}' AND date = '${date}';")

        return queryManager.runBigQuery(sqlText.toString())
    }

    @Override
    TableResult getDeathGrowthByCountry(String countryName) {
        StringBuilder sqlText = new StringBuilder("SELECT date, SUM(deaths) AS country_deaths_growth FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE UPPER(country_region) = '${countryName}' ")
                .append("GROUP BY date ")
                .append("HAVING SUM(deaths) IS NOT NULL ")
                .append("ORDER BY date ASC;")

        return queryManager.runBigQuery(sqlText.toString())
    }
}
