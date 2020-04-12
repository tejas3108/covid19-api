package com.tejas.covid19api.lib.manager.impl

import com.google.cloud.bigquery.BigQuery
import com.google.cloud.bigquery.BigQueryOptions
import com.google.cloud.bigquery.Job
import com.google.cloud.bigquery.JobId
import com.google.cloud.bigquery.JobInfo
import com.google.cloud.bigquery.QueryJobConfiguration
import com.google.cloud.bigquery.TableResult
import com.tejas.covid19api.lib.manager.BigQueryManager
import groovy.transform.CompileStatic
import org.springframework.stereotype.Service

@CompileStatic
@Service
class BigQueryManagerImpl implements BigQueryManager {
    BigQuery query

    BigQueryManagerImpl() {
        //this will initialize the bigQuery instance from the default JSON file from GCP
        query = BigQueryOptions.getDefaultInstance().getService()
    }

    @Override
    QueryJobConfiguration getQueryJobConfiguration(String sqlText) {
        return QueryJobConfiguration.newBuilder(sqlText)
                .setUseLegacySql(false)
                .build()
    }

    @Override
    Job getQueryJob(QueryJobConfiguration queryConfig) {
        // Create a job ID so that we can safely retry.
        JobId jobId = JobId.of(UUID.randomUUID().toString())
        return query.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build())
    }

    @Override
    void handleErrors(Job queryJob) {
        // Check for errors
        if (queryJob == null) {
            throw new RuntimeException("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            // You can also look at queryJob.getStatus().getExecutionErrors() for all
            // errors, not just the latest one.
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }
    }

    @Override
    TableResult runBigQuery(String sqlText) throws RuntimeException {
        QueryJobConfiguration queryConfig = getQueryJobConfiguration(sqlText)
        Job queryJob = getQueryJob(queryConfig)

        // Wait for the query to complete.
        queryJob = queryJob.waitFor()

        handleErrors(queryJob)

        // Return the results.
        return queryJob.getQueryResults()
    }
}
