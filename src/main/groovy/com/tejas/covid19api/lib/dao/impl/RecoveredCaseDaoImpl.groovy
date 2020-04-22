package com.tejas.covid19api.lib.dao.impl

import com.google.cloud.bigquery.TableResult
import com.tejas.covid19api.lib.constants.DatabaseConstants
import com.tejas.covid19api.lib.dao.RecoveredCaseDao
import com.tejas.covid19api.lib.manager.BigQueryManager
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
class RecoveredCaseDaoImpl implements RecoveredCaseDao {
    @Autowired
    BigQueryManager queryManager

    @Override
    TableResult getTotalRecovered() {
        StringBuilder sqlText = new StringBuilder("SELECT SUM(recovered) AS total_recovered FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE ${DatabaseConstants.LATEST_DATE_QUERY_STRING} ")
                .append("HAVING SUM(recovered) IS NOT NULL;")

        return queryManager.runBigQuery(sqlText.toString())
    }

    @Override
    TableResult getRecoveredByCountry(String countryName) {
        StringBuilder sqlText = new StringBuilder("SELECT SUM(recovered) AS total_recovered_country FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE UPPER(country_region) = '${countryName}' AND ${DatabaseConstants.LATEST_DATE_QUERY_STRING} ")
                .append("HAVING SUM(recovered) IS NOT NULL;")

        return queryManager.runBigQuery(sqlText.toString())
    }

    @Override
    TableResult getRecoveredTillDateByCountry(String countryName, String date) {
        StringBuilder sqlText = new StringBuilder("SELECT SUM(recovered) AS recovered_till_date FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE UPPER(country_region) = '${countryName}' AND date = '${date}' ")
                .append("HAVING SUM(recovered) IS NOT NULL;")

        return queryManager.runBigQuery(sqlText.toString())
    }

    @Override
    TableResult getTotalRecoveredTillDate(String date) {
        StringBuilder sqlText = new StringBuilder("SELECT SUM(recovered) AS recovered_till_date FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE date = '${date}' ")
                .append("HAVING SUM(recovered) IS NOT NULL;")

        return queryManager.runBigQuery(sqlText.toString())
    }

    @Override
    TableResult getRecoveredGrowthByCountry(String countryName) {
        StringBuilder sqlText = new StringBuilder("SELECT date, SUM(recovered) AS country_recovered_growth FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE} ")
                .append("WHERE UPPER(country_region) = '${countryName}' ")
                .append("GROUP BY date ")
                .append("HAVING SUM(recovered) IS NOT NULL ")
                .append("ORDER BY date ASC;")

        return queryManager.runBigQuery(sqlText.toString())
    }
}
