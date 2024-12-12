package dev.suai.randomcoffee.data.meets

import dev.suai.randomcoffee.domain.HistoryRepository
import dev.suai.randomcoffee.domain.entity.HistoryMeet
import dev.suai.randomcoffee.domain.entity.TgHandle
import dev.suai.randomcoffee.schema.api.MeetApi
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject


class HistoryApiRepository @Inject constructor(
    private val api: MeetApi,
) : HistoryRepository {
    override suspend fun getHistory(): List<HistoryMeet> {
        try {
            val response = api.meetHistoryGet()
            if (response.isSuccessful && response.body() != null) {
                val history = response.body()?.map {
                    HistoryMeet(
                        date = if (it.date != null) Date.from(
                            it.date.atStartOfDay(ZoneId.systemDefault()).toInstant()
                        ) else Date(),
                        name = it.secondName ?: "___",
                        tg = TgHandle(it.secondTgHandle ?: "durov"),
                    )
                } ?: emptyList()

                return history
            } else {
                return emptyList()
            }
        } catch (e: Exception) {
            return emptyList()
        }
    }
}
