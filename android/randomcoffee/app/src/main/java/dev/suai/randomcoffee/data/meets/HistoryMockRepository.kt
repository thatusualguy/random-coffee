package dev.suai.randomcoffee.data.meets

import dev.suai.randomcoffee.domain.HistoryRepository
import dev.suai.randomcoffee.domain.entity.Meet
import java.util.Date
import javax.inject.Inject


class HistoryMockRepository @Inject constructor() : HistoryRepository {
    override suspend fun getHistory(): List<Meet> {
        return listOf(
            Meet(Date(2024, 11, 13), "Максим", "thatusualguy"),
            Meet(Date(2024, 11, 12), "Никита", "Closed_56"),
            Meet(Date(2024, 11, 11), "Алексей", "sslowerr"),
            Meet(Date(2024, 10, 25), "Булат", "googlewaitme"),
        ).sortedByDescending { it.date }
    }
}