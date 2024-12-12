package dev.suai.randomcoffee.domain

import dev.suai.randomcoffee.domain.entity.HistoryMeet

interface HistoryRepository {
    suspend fun getHistory(): List<HistoryMeet>
}