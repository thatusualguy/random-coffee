package dev.suai.randomcoffee.data.calendar

import dev.suai.randomcoffee.domain.CalendarRepository
import dev.suai.randomcoffee.domain.ChangeDate
import java.time.LocalDate
import javax.inject.Inject

class CalendarMockRepository @Inject constructor() : CalendarRepository {

    private var odd  = false
    private val plannedMeets: MutableList<LocalDate> = mutableListOf(
        LocalDate.now().plusWeeks(1),
        LocalDate.now().plusDays(13),
        LocalDate.now().plusDays(2)
    )

    override suspend fun getCalendar(): List<LocalDate> {
        println(plannedMeets)
        return plannedMeets
    }

    override suspend fun changeDateState(changes: ChangeDate): List<LocalDate> {
        odd = !odd
        if (!odd)
            return plannedMeets

        println(changes)

        for (data in changes.toAdd) {
            if (data !in plannedMeets)
                plannedMeets.add(data)
        }
        for (data in changes.toDelete) {
            if (data in plannedMeets)
                plannedMeets.remove(data)
        }

        return plannedMeets
    }

}