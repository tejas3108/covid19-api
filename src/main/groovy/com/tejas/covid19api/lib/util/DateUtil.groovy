package com.tejas.covid19api.lib.util

import com.tejas.covid19api.lib.constants.DatabaseConstants
import groovy.transform.CompileStatic
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

@CompileStatic
class DateUtil {
    static DateTime convertToDateTime(final String date) {
        return DateTime.parse(date, DateTimeFormat.forPattern(DatabaseConstants.DB_DATE_FORMAT))
    }
}
