package com.tejas.covid19api.lib.service.impl

import com.google.auth.oauth2.ServiceAccountCredentials
import com.google.cloud.bigquery.BigQuery
import com.google.cloud.bigquery.BigQueryOptions
import com.google.cloud.bigquery.FieldValueList
import com.google.cloud.bigquery.Job
import com.google.cloud.bigquery.JobId
import com.google.cloud.bigquery.JobInfo
import com.google.cloud.bigquery.QueryJobConfiguration
import com.google.cloud.bigquery.TableResult
import com.tejas.covid19api.lib.service.BaseService
import groovy.transform.CompileStatic
import org.springframework.stereotype.Service

@CompileStatic
@Service
class BaseServiceImpl implements BaseService {

    @Override
    String getTotalDeaths() {
        BigQuery query = BigQueryOptions.getDefaultInstance().getService()
        QueryJobConfiguration queryConfig =
                QueryJobConfiguration.newBuilder(
                "select COUNT(confirmed) AS total_deaths FROM bigquery-public-data.covid19_jhu_csse.summary;")
                        .setUseLegacySql(false)
                        .build();

        // Create a job ID so that we can safely retry.
        JobId jobId = JobId.of(UUID.randomUUID().toString());
        Job queryJob = query.create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());

        // Wait for the query to complete.
        queryJob = queryJob.waitFor();

        // Check for errors
        if (queryJob == null) {
            throw new RuntimeException("Job no longer exists");
        } else if (queryJob.getStatus().getError() != null) {
            // You can also look at queryJob.getStatus().getExecutionErrors() for all
            // errors, not just the latest one.
            throw new RuntimeException(queryJob.getStatus().getError().toString());
        }

        // Get the results.
        TableResult result = queryJob.getQueryResults();

        // Print all pages of the results.
        for (FieldValueList row : result.iterateAll()) {
            long viewCount = row.get("total_deaths").getLongValue();
            System.out.printf("Total Deaths: %d%n", viewCount);
        }

        return "TEST DEATHS"
    }
}
