package com.amper.personapp.util

import android.icu.util.Calendar
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtility {

    const val MONTH_DAY_YEAR = "MMM dd, yyyy"
    const val DATE_RESPONSE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"


    fun toDate(stringDate: String, dateFormat: String = DATE_RESPONSE_FORMAT): Date? {
        val currentDateFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        return currentDateFormat.parse(stringDate)
    }

    fun toDateFormat(stringDate: String, dateFormat: String = MONTH_DAY_YEAR): String? {
        val currentDateFormat = SimpleDateFormat(DATE_RESPONSE_FORMAT, Locale.ENGLISH)
        val newFormat = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        return currentDateFormat.parse(stringDate)?.let {
            newFormat.format(it)
        }
    }

    fun getYearsDifference(date1: Date, date2: Date): Int {
        val calendar = Calendar.getInstance()
        calendar.time = date1
        return calendar.fieldDifference(date2, Calendar.YEAR)
    }

}