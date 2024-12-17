package dev.suai.randomcoffee.data.meets

import dev.suai.randomcoffee.domain.HistoryRepository
import dev.suai.randomcoffee.domain.entity.Meet
import dev.suai.randomcoffee.schema.api.MeetApi
import java.time.ZoneId
import java.util.Date
import javax.inject.Inject


class HistoryApiRepository @Inject constructor(
    private val api: MeetApi,
) : HistoryRepository {
    override suspend fun getHistory(): List<Meet> {
        try {
            val response = api.meetHistoryGet()
            if (response.isSuccessful && response.body() != null) {
                val history = response.body()?.map {
                    Meet(
                        date = if (it.date != null) Date.from(
                            it.date.atStartOfDay(ZoneId.systemDefault()).toInstant()
                        ) else Date(),
                        name = it.secondName ?: "___",
                        tg = it.secondTgHandle ?: "durov",
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