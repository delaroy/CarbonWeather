package com.carbon.carbonweather.utils

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.TimeZone

fun convertLongToTime(time: Long): String {
    val format = SimpleDateFormat("MMM d, hh:mm a")
    val date = Date(time * 1000)
    return format.format(date)
}

fun dayStringFormat(secs: Long): String? {
    val cal = GregorianCalendar()
    cal.time = Date(secs * 1000)
    when (cal[Calendar.DAY_OF_WEEK]) {
        Calendar.MONDAY -> return "Mon"
        Calendar.TUESDAY -> return "Tue"
        Calendar.WEDNESDAY -> return "Wed"
        Calendar.THURSDAY -> return "Thu"
        Calendar.FRIDAY -> return "Fri"
        Calendar.SATURDAY -> return "Sat"
        Calendar.SUNDAY -> return "Sun"
    }
    return "Unknown"
}

fun convertToForecastDate(time: Long): String {
    val date = Date(time * 1000)
    val format = SimpleDateFormat("MMM d")
    val day = dayStringFormat(time)
    return day + ", " + format.format(date)
}