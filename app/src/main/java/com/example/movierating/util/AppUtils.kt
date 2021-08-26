package com.example.movierating.util

import java.lang.Exception
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    /**
     * method helps to convert one  date format into another format
     * @param dateString input date string
     * @return it will return the converted date string
     */
    fun convertDateFormat(dateString: String): String? {
        return try {
            val originalFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
            val targetFormat: DateFormat = SimpleDateFormat("MMM yy", Locale.ENGLISH)
            val date: Date? = originalFormat.parse(dateString)
            date?.let {
                targetFormat.format(it)
            }
        } catch (e: Exception) {
            ""
        }
    }
}
