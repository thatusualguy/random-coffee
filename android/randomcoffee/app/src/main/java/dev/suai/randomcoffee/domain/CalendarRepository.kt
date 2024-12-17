package dev.suai.randomcoffee.domain

import java.time.LocalDate

data class ChangeDate(val toAdd: List<LocalDate>, val toDelete: List<LocalDate>)

interface CalendarRepository {
    suspend fun getCalendar(): List<LocalDate>
    suspend fun changeDateState(changes: ChangeDate): List<LocalDate>
}