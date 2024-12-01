package com.example.to_do.model

import com.example.to_do.data.months
import java.util.Calendar

data class Time(val year: Int = 2000, val month: Int = 12, val day: Int = 12) {

    override fun toString(): String {
        val dayString: String = if(day < 10){
            "0$day"
        } else {
            day.toString()
        }

        return "$year-$month-$dayString"
    }

    companion object {
        fun stringToTime(time: String): Time {
            return Time(
                year = time.substring(0, 4).toInt(),
                month = time.substring(5, 7).toInt(),
                day = time.substring(8, 10).toInt()
            )
        }
    }

    fun getUiDate(): String {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val month = months[month - 1].name.substring(0, 3)

        return if (year > currentYear) {
            "$$day $month $year"
        } else {
            "$day $month"
        }
    }

}

data class HourMinute(val hour: Int = 12, val minute: Int = 0) {
    override fun toString(): String {

        val hourString: String = if(hour < 10){
            "0$hour"
        } else {
            hour.toString()
        }

        val minuteString: String = if(minute < 10){
            "0$minute"
        } else {
            minute.toString()
        }

        return "$hourString:$minuteString"
    }
    companion object {
        fun stringToHourMinute(hourMinute: String): HourMinute {
            return HourMinute(
                hour = hourMinute.substring(0, 2).toInt(),
                minute = hourMinute.substring(3, 5).toInt()
            )
        }
    }
}