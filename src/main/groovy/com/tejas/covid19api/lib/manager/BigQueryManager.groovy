package com.tejas.covid19api.lib.manager

import com.google.cloud.bigquery.Job
import com.google.cloud.bigquery.QueryJobConfiguration
import com.google.cloud.bigquery.TableResult

interface BigQueryManager {
    QueryJobConfiguration getQueryJobConfiguration(String sqlText)

    Job getQueryJob(QueryJobConfiguration queryConfig)

    void handleErrors(Job queryJob)

    TableResult runBigQuery(String sqlText) throws RuntimeException
}