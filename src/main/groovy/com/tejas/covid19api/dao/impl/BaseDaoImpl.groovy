package com.tejas.covid19api.dao.impl

import com.google.cloud.bigquery.TableResult
import com.tejas.covid19api.dao.BaseDao
import com.tejas.covid19api.lib.constants.DatabaseConstants
import com.tejas.covid19api.lib.manager.BigQueryManager
import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository

@CompileStatic
@Repository
class BaseDaoImpl implements BaseDao{
    @Autowired
    BigQueryManager queryManager

    @Override
    TableResult getTotalDeaths() {
        return queryManager.runBigQuery("SELECT SUM(deaths) AS total_deaths FROM ${DatabaseConstants.DB_NAME}.${DatabaseConstants.SUMMARY_TABLE};")
    }
}
