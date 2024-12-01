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



