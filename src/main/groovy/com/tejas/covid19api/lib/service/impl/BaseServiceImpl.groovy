package com.tejas.covid19api.lib.service.impl

import com.google.cloud.bigquery.FieldValueList
import com.google.cloud.bigquery.TableResult
import com.tejas.covid19api.lib.manager.BigQueryManager
import com.tejas.covid19api.lib.service.BaseService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@CompileStatic
@Service
class BaseServiceImpl implements BaseService {

    @Autowired
    BigQueryManager queryManager

    @Override
    String getTotalDeaths() {
        TableResult result = queryManager.runBigQuery("select COUNT(confirmed) AS total_deaths FROM bigquery-public-data.covid19_jhu_csse.summary;")
        long totalDeathCount
        for (FieldValueList row : result.iterateAll()) {
            totalDeathCount = row.get("total_deaths").getLongValue()
        }

        return String.valueOf(totalDeathCount)
    }
}
