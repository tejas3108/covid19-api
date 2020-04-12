package com.tejas.covid19api.dao

import com.google.cloud.bigquery.TableResult
import groovy.transform.CompileStatic

@CompileStatic
interface BaseDao {
    TableResult getTotalDeaths()
}