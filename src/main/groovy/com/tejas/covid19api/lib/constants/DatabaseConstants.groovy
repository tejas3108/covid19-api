package com.tejas.covid19api.lib.constants

import groovy.transform.CompileStatic

@CompileStatic
class DatabaseConstants {
    final static String DB_NAME = 'bigquery-public-data.covid19_jhu_csse'
    final static String SUMMARY_TABLE = 'summary'
}
