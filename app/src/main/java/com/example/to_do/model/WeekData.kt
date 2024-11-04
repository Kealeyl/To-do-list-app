package com.example.to_do.model

data class WeekData(
    val firstDay: Int,
    val month: Month,
    val weekDays: Array<Pair<String, Int>>
) {
    override fun toString(): String {
        val endDay = firstDay + 6
        return "$firstDay $month - $endDay $month"
    }
}

enum class Month {
    January,
    February,
    March,
    April,
    May,
    June,
    July,
    August,
    September,
    October,
    November,
    December
}

enum class WeekDays {
    Sun,
    Mon,
    Tue,
    Wed,
    Thu,
    Fri,
    Sat
}

// Need to account for starting new month
// Returns array of pairs of week day and day
fun arrayOfWeekDays(startDate: Int): Array<Pair<String, Int>> {
    val weekDays = WeekDays.values()
    return Array(7) {
        weekDays[it].name to (startDate + it)
    }
}

