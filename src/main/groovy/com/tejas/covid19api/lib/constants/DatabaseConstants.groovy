package com.tejas.covid19api.lib.constants

import groovy.transform.CompileStatic

@CompileStatic
class DatabaseConstants {
    final static String DB_NAME = 'bigquery-public-data.covid19_jhu_csse'
    final static String SUMMARY_TABLE = 'summary'
    final static String DB_DATE_FORMAT = 'YYYY-MM-DD'

    final static String latestDateQueryString = "date = (SELECT MAX(date) FROM ${DB_NAME}.${SUMMARY_TABLE})"
}
