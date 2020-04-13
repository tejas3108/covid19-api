package com.tejas.covid19api.lib.dao

import com.google.cloud.bigquery.TableResult
import groovy.transform.CompileStatic

@CompileStatic
interface DeathCaseDao {
    TableResult getTotalDeaths()

    TableResult getDeathsByCountry(String countryName)

    TableResult getDeathGrowthByCountry(String countryName)
}