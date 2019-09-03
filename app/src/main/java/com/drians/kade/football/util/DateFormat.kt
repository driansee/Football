package com.drians.kade.football.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun strTodate(strDate: String?, pattern: String = "yyyy-MM-dd"): Date {
    val format = SimpleDateFormat(pattern)
    return format.parse(strDate)
}

@SuppressLint("SimpleDateFormat")
fun changeFormatDate(date: Date?): String? = with(date ?: Date()) {
    SimpleDateFormat("EEE, dd MMM yyy", Locale.ENGLISH).format(this)
}

@SuppressLint("SimpleDateFormat")
fun toGMTFormat(date: String?, time: String?): Date? {
    val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    formatter.timeZone = TimeZone.getTimeZone("UTC")
    val dateTime = "$date $time"
    return formatter.parse(dateTime)
}