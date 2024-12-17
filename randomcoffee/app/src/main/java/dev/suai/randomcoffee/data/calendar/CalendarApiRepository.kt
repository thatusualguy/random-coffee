package dev.suai.randomcoffee.data.calendar

import dev.suai.randomcoffee.domain.CalendarRepository
import dev.suai.randomcoffee.domain.ChangeDate
import dev.suai.randomcoffee.schema.api.ScheduleApi
import dev.suai.randomcoffee.schema.model.ScheduleSlot
import dev.suai.randomcoffee.schema.model.UserScheduleChange
import java.time.LocalDate
import java.util.logging.Logger
import javax.inject.Inject

class CalendarApiRepository @Inject constructor(private val scheduleApi: ScheduleApi) :
    CalendarRepository {
    private val logger = Logger.getLogger(CalendarApiRepository::class.java.name)


    override suspend fun getCalendar(): List<LocalDate> {
        val response = scheduleApi.scheduleGet(startDate = LocalDate.now(), endDate = null)
        if (!response.isSuccessful || response.body() == null) {
            logger.info("Error getting calendar: ${response.message()}")
            return emptyList()
        }

        return response.body()!!.map { it.date ?: LocalDate.now() }
    }

    override suspend fun changeDateState(changes: ChangeDate): List<LocalDate> {
        val userScheldueChange = UserScheduleChange(
            toAdd = changes.toAdd.map { ScheduleSlot(it) },
            toDelete = changes.toDelete.map { ScheduleSlot(it) },
        )
        val response = scheduleApi.schedulePost(userScheldueChange)
        logger.info("Posting calendar: ${response.message()}")

        return getCalendar()
    }
}