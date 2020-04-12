package com.tejas.covid19api.lib.service.impl

import com.google.cloud.bigquery.FieldValueList
import com.google.cloud.bigquery.TableResult
import com.tejas.covid19api.dao.BaseDao
import com.tejas.covid19api.lib.manager.BigQueryManager
import com.tejas.covid19api.lib.service.BaseService
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@CompileStatic
@Service
class BaseServiceImpl implements BaseService {
    @Autowired
    BaseDao dao

    @Override
    String getTotalDeaths() {
        TableResult result = dao.getTotalDeaths()
        long totalDeathCount
        for (FieldValueList row : result.iterateAll()) {
            totalDeathCount = row.get("total_deaths").getLongValue()
        }

        return String.valueOf(totalDeathCount)
    }
}
