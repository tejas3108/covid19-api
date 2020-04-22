package com.tejas.covid19api.lib.dao

import com.google.cloud.bigquery.TableResult
import groovy.transform.CompileStatic

@CompileStatic
interface CaseSummaryDao {
    TableResult getTotalSummary()

    TableResult getSummaryByCountry(String countryName)

    TableResult getTotalSummaryTillDate(String date)

    TableResult getSummaryGrowthByCountry(String countryName)

    TableResult getSummaryTillDateByCountry(String countryName, String date)
}