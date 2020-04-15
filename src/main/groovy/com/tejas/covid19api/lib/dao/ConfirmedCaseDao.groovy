package com.tejas.covid19api.lib.dao

import com.google.cloud.bigquery.TableResult
import groovy.transform.CompileStatic

@CompileStatic
interface ConfirmedCaseDao {
    TableResult getTotalConfirmed()

    TableResult getConfirmedByCountry(String countryName)

    TableResult getTotalConfirmedTillDate(String date)

    TableResult getConfirmedGrowthByCountry(String countryName)

    TableResult getConfirmedTillDateByCountry(String countryName, String date)
}