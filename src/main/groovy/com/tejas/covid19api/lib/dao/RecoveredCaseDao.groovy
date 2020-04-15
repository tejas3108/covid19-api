package com.tejas.covid19api.lib.dao

import com.google.cloud.bigquery.TableResult
import groovy.transform.CompileStatic

@CompileStatic
interface RecoveredCaseDao {
    TableResult getTotalRecovered()

    TableResult getRecoveredByCountry(String countryName)

    TableResult getTotalRecoveredTillDate(String date)

    TableResult getRecoveredGrowthByCountry(String countryName)

    TableResult getRecoveredTillDateByCountry(String countryName, String date)
}